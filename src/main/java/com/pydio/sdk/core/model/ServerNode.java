package com.pydio.sdk.core.model;


import com.pydio.sdk.core.common.errors.Code;
import com.pydio.sdk.core.common.errors.Error;
import com.pydio.sdk.core.security.CertificateTrust;
import com.pydio.sdk.core.security.CertificateTrustManager;

import org.json.JSONObject;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URI;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.net.ssl.HttpsURLConnection;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLException;
import javax.net.ssl.TrustManager;

/**
 * Class that wrap a server properties
 *
 * @author pydio
 */

public class ServerNode implements Node {

    public Map<String, WorkspaceNode> workspaces;
    private boolean legacy = false;
    private String scheme = null;
    private String host = null;
    private int port = 80;
    private String path = null;
    private String version = null;
    private String versionName = null;
    private String iconURL;
    private String welcomeMessage;
    private String label = null;
    private String url = null;
    private boolean sslUnverified = false;
    private SSLContext sslContext;
    private Properties properties = null;
    private JSONObject bootConf;
    private X509Certificate[] certificateChain;
    private CertificateTrust.Helper givenTrustHelper, trustHelper;

    public ServerNode() {
    }

    public static ServerNode fromAddress(String address) throws IOException {
        URL url = new URL(address);
        ServerNode node = new ServerNode();
        node.scheme = url.getProtocol();
        node.host = url.getHost();
        node.port = url.getPort();
        node.path = url.getPath();
        node.url = address;
        return node;
    }

    //********************************************************************************************
    //                  Super class: NODE METHODS
    //********************************************************************************************
    @Override
    public String getProperty(String key) {
        if (properties == null) return null;
        return properties.getProperty(key);
    }

    @Override
    public void setProperty(String key, String value) {
        if (properties == null) {
            properties = new Properties();
        }
        properties.setProperty(key, value);
    }

    @Override
    public void deleteProperty(String key) {
        if (properties != null && properties.contains(key)) {
            properties.remove(key);
        }
    }

    @Override
    public String path() {
        return path;
    }

    @Override
    public String label() {
        return label;
    }

    @Override
    public int type() {
        return Node.TYPE_SERVER;
    }

    @Override
    public String id() {
        String id = scheme + "://" + host;
        if (port != 80) {
            id = id + ":" + port;
        }
        id = id + path;
        return id;
    }

    @Override
    public void setProperties(Properties p) {
        properties = p;
    }

    @Override
    public String getEncoded() {
        return null;
    }

    @Override
    public String getEncodedHash() {
        return null;
    }

    @Override
    public int compare(Node node) {
        return 0;
    }

    //*****************************************************************************
    //						RESOLVE
    //*****************************************************************************
    public Error resolve(String address, boolean unverifiedSSL, CertificateTrust.Helper h) {
        return resolveRemote(address, unverifiedSSL, h);
    }

    private Error resolveRemote(final String address, boolean unverifiedSSL, final CertificateTrust.Helper h) {
        URL url;
        try {
            url = new URL(address);
        } catch (MalformedURLException e) {
            return new Error(Code.bad_uri);
        }

        scheme = url.getProtocol();
        host = url.getHost();
        port = url.getPort();
        path = url.getPath();
        this.url = address;
        setUnverifiedSSL(unverifiedSSL);
        setCertificateTrustHelper(h);

        int err = downloadBootConf("a/frontend/bootconf");
        if (err == 0) {
            return null;
        }

        if (err != Code.unexpected_response) {
            err = downloadBootConf("index.php?get_action=get_boot_conf");
            if (err == 0) {
                //c.onComplete(new Error(Pydio.ERROR_PYDIO_8_SERVER, "found pydio 8 server", null));
                return null;
            }
        }
        return new Error(Code.not_pydio_server);
    }

    private int downloadBootConf(String apiURLTail) {
        InputStream in;
        SSLContext sslCtx;
        HttpURLConnection con;


        String apiURL = url();
        boolean addressEndsWithSlash = apiURL.endsWith("/");
        boolean tailStartsWithSlash = apiURLTail.startsWith("/");

        if (addressEndsWithSlash && tailStartsWithSlash) {
            apiURL = apiURL + apiURLTail.substring(1);
        } else if (!addressEndsWithSlash && !tailStartsWithSlash) {
            apiURL = apiURL + "/" + apiURLTail;
        } else {
            apiURL = apiURL + apiURLTail;
        }

        if (unVerifiedSSL()) {
            try {
                sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, new TrustManager[]{trustManager()}, null);
            } catch (Exception e) {
                return Code.tls_init;
            }

            HttpsURLConnection scon;
            try {
                scon = (HttpsURLConnection) new URL(apiURL).openConnection();
            } catch (IOException e) {
                return Code.con_failed;
            }
            scon.setSSLSocketFactory(sslContext.getSocketFactory());
            con = scon;
        } else {
            try {
                con = (HttpURLConnection) new URL(apiURL).openConnection();
            } catch (IOException e) {
                return Code.con_failed;
            }
        }

        try {
            in = con.getInputStream();
        } catch (IOException e) {
            if (e instanceof SSLException) {
                return Code.ssl_error;
            }
            return Code.con_failed;

        } catch (Exception e) {
            if (e instanceof IllegalArgumentException && e.getMessage().toLowerCase().contains("unreachable")) {
                return Code.unreachable_host;
            }
            return Code.con_failed;
        }


        ByteArrayOutputStream out = new ByteArrayOutputStream();
        byte[] buffer = new byte[4096];
        for (; ; ) {
            int n = 0;
            try {
                n = in.read(buffer);
            } catch (IOException e) {
                return Code.con_failed;
            }
            if (n == -1) {
                break;
            }
            out.write(buffer, 0, n);
        }

        try {
            bootConf = new JSONObject(new String(out.toByteArray(), "UTF-8"));
        } catch (Exception ignored) {
            return Code.unexpected_response;
        }

        boolean isCells = bootConf.has("backend");
        version = bootConf.getString("ajxpVersion");
        versionName = isCells ? "cells" : "pydio";

        JSONObject customWordings = bootConf.getJSONObject("customWording");
        label = customWordings.getString("title");
        iconURL = customWordings.getString("icon");

        tailStartsWithSlash = iconURL.startsWith("/");
        if (addressEndsWithSlash && tailStartsWithSlash) {
            iconURL = url() + iconURL;
        } else if (!addressEndsWithSlash && !tailStartsWithSlash) {
            iconURL = url() + "/" + iconURL;
        } else {
            iconURL = url() + iconURL;
        }

        if (customWordings.has("welcomeMessage")) {
            welcomeMessage = customWordings.getString("welcomeMessage");
        }
        return 0;
    }


    //*****************************************************************************
    //						PROPERTIES
    //*****************************************************************************
    private TrustManager trustManager() {
        return new CertificateTrustManager(trustHelper);
    }

    public String version() {
        if (version == null) {
            boolean isCells = bootConf.has("backend");
            version = bootConf.getString("ajxpVersion");
            versionName = isCells ? "cells" : "pydio";
        }
        return version;
    }

    public String versionName() {
        if (versionName == null) {
            boolean isCells = bootConf.has("backend");
            version = bootConf.getString("ajxpVersion");
            versionName = isCells ? "cells" : "pydio";
        }
        return versionName;
    }

    public CertificateTrust.Helper getTrustHelper() {
        if (trustHelper == null) {
            return trustHelper = new CertificateTrust.Helper() {
                @Override
                public boolean isServerTrusted(X509Certificate[] chain) {
                    certificateChain = chain;
                    return givenTrustHelper != null && givenTrustHelper.isServerTrusted(chain);
                }

                @Override
                public X509Certificate[] getAcceptedIssuers() {
                    if (givenTrustHelper != null) {
                        return givenTrustHelper.getAcceptedIssuers();
                    }
                    return new X509Certificate[0];
                }
            };
        }
        return trustHelper;
    }

    public ServerNode init(String url) {
        if (!url.endsWith("/")) {
            url += "/";
        }
        this.url = url;
        URI uri = URI.create(url);
        scheme = uri.getScheme();
        host = uri.getHost();
        path = uri.getPath();
        port = uri.getPort();
        return this;
    }

    public ServerNode init(String url, CertificateTrust.Helper helper) {
        this.init(url);
        givenTrustHelper = helper;
        return this;
    }

    public ServerNode init(String url, String user, CertificateTrust.Helper helper) {
        this.init(url);
        //mUser = user;
        givenTrustHelper = helper;
        return this;
    }

    public boolean legacy() {
        return legacy;
    }

    public boolean unVerifiedSSL() {
        return sslUnverified;
    }

    public String host() {
        return host;
    }

    public String scheme() {
        return scheme;
    }

    public int port() {
        return port;
    }

    public String iconURL() {
        return iconURL;
    }

    public String url() {
        if (url != null) return url;
        String path = scheme.toLowerCase() + "://" + host;
        if (port > 0 && port != 80) {
            path += ":" + port;
        }
        path += path();
        if (!path.endsWith("/"))
            return path + "/";
        return url = path;
    }

    public String welcomeMessage() {
        return welcomeMessage;
    }

    public String apiURL() {
        return bootConf.getString("ENDPOINT_REST_API");
    }

    public SSLContext getSslContext() {
        if (sslContext == null) {
            try {
                sslContext = SSLContext.getInstance("TLS");
                sslContext.init(null, new TrustManager[]{trustManager()}, null);
            } catch (Exception e) {
                return null;
            }
        }
        return sslContext;
    }

    public boolean equals(Object o) {
        try {
            return this == o || (o instanceof Node) && ((Node) o).type() == type() && label().equals(((Node) o).label()) && path().equals(((Node) o).path());
        } catch (NullPointerException e) {
            return false;
        }
    }

    public WorkspaceNode getWorkspace(String id) {
        if (workspaces != null && workspaces.containsKey(id)) {
            return workspaces.get(id);
        }
        return null;
    }

    public ServerNode setUnverifiedSSL(boolean unverified) {
        sslUnverified = unverified;
        return this;
    }

    public ServerNode setLabel(String label) {
        this.label = label;
        return this;
    }

    public ServerNode setWorkspaces(List<WorkspaceNode> nodes) {
        if (this.workspaces == null) {
            this.workspaces = new HashMap<>();
        } else {
            this.workspaces.clear();
        }

        for (WorkspaceNode wn : nodes) {
            this.workspaces.put(wn.getId(), wn);
        }
        return this;
    }

    public ServerNode setCertificateTrustHelper(CertificateTrust.Helper helper) {
        givenTrustHelper = helper;
        return this;
    }
}