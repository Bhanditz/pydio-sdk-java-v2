package com.pydio.sdk.core;

import com.google.gson.Gson;
import com.pydio.sdk.core.api.cells.ApiClient;
import com.pydio.sdk.core.api.cells.ApiException;
import com.pydio.sdk.core.api.cells.api.ChangeServiceApi;
import com.pydio.sdk.core.api.cells.api.FrontendServiceApi;
import com.pydio.sdk.core.api.cells.api.JobsServiceApi;
import com.pydio.sdk.core.api.cells.api.ShareServiceApi;
import com.pydio.sdk.core.api.cells.api.TreeServiceApi;
import com.pydio.sdk.core.api.cells.model.RestBulkMetaResponse;
import com.pydio.sdk.core.api.cells.model.RestChangeCollection;
import com.pydio.sdk.core.api.cells.model.RestChangeRequest;
import com.pydio.sdk.core.api.cells.model.RestCreateNodesRequest;
import com.pydio.sdk.core.api.cells.model.RestDeleteNodesRequest;
import com.pydio.sdk.core.api.cells.model.RestFrontSessionRequest;
import com.pydio.sdk.core.api.cells.model.RestFrontSessionResponse;
import com.pydio.sdk.core.api.cells.model.RestGetBulkMetaRequest;
import com.pydio.sdk.core.api.cells.model.RestNodesCollection;
import com.pydio.sdk.core.api.cells.model.RestPutShareLinkRequest;
import com.pydio.sdk.core.api.cells.model.RestRestoreNodesRequest;
import com.pydio.sdk.core.api.cells.model.RestShareLink;
import com.pydio.sdk.core.api.cells.model.RestShareLinkAccessType;
import com.pydio.sdk.core.api.cells.model.RestUserJobRequest;
import com.pydio.sdk.core.api.cells.model.TreeNode;
import com.pydio.sdk.core.api.cells.model.TreeNodeType;
import com.pydio.sdk.core.api.cells.model.TreeSyncChange;
import com.pydio.sdk.core.common.callback.ChangeHandler;
import com.pydio.sdk.core.common.callback.NodeHandler;
import com.pydio.sdk.core.common.callback.RegistryItemHandler;
import com.pydio.sdk.core.common.callback.TransferProgressListener;
import com.pydio.sdk.core.common.errors.Code;
import com.pydio.sdk.core.common.errors.SDKException;
import com.pydio.sdk.core.model.parser.RegistrySaxHandler;
import com.pydio.sdk.core.model.parser.ServerGeneralRegistrySaxHandler;
import com.pydio.sdk.core.model.parser.WorkspaceNodeSaxHandler;
import com.pydio.sdk.core.model.Change;
import com.pydio.sdk.core.model.ChangeNode;
import com.pydio.sdk.core.model.FileNode;
import com.pydio.sdk.core.model.Message;
import com.pydio.sdk.core.model.ServerNode;
import com.pydio.sdk.core.model.Stats;
import com.pydio.sdk.core.model.Token;
import com.pydio.sdk.core.security.Credentials;
import com.pydio.sdk.core.utils.io;
import com.squareup.okhttp.OkHttpClient;

import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.helpers.DefaultHandler;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.security.cert.X509Certificate;
import java.text.ParseException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.net.ssl.SSLContext;
import javax.xml.parsers.SAXParser;
import javax.xml.parsers.SAXParserFactory;

public class PydioCells implements Client {

    private final static Object lock = new Object();

    public String URL;
    private String apiURL;
    protected String JWT;
    private ServerNode serverNode;
    private Credentials credentials;

    private Token.Provider tokenProvider;
    private Token.Store tokenStore;

    public PydioCells(ServerNode node) {
        this.serverNode = node;
        this.URL = node.url();
        this.apiURL = node.apiURL();
    }

    protected void getJWT() throws SDKException {

        Token t = null;
        String subject = String.format("%s@%s", credentials.getLogin(), serverNode.url());
        if (tokenProvider != null) {
            t = tokenProvider.get(subject);
        }

        if (t == null || t.isNotValid()) {
            synchronized (lock) {
                ApiClient apiClient = getApiClient();
                String password = credentials.getPassword();

                if (password == null) {
                    throw new SDKException(Code.authentication_required, new IOException("no password provided"));
                }

                RestFrontSessionRequest request = new RestFrontSessionRequest();
                request.setClientTime((int) System.currentTimeMillis());

                Map<String, String> authInfo = new HashMap<>();
                authInfo.put("login", credentials.getLogin());
                authInfo.put("password", password);
                authInfo.put("type", "credentials");
                request.authInfo(authInfo);

                FrontendServiceApi api = new FrontendServiceApi(apiClient);
                RestFrontSessionResponse response;
                try {
                    response = api.frontSession(request);
                } catch (ApiException e) {
                    throw new SDKException(e);
                }

                t = new Token();
                t.subject = subject;
                t.value = response.getJWT();
                long expireIn = (long) response.getExpireTime();
                t.expiry = System.currentTimeMillis() + expireIn * 1000;

                if (this.tokenStore != null) {
                    this.tokenStore.set(t);
                }
            }
        }
        this.JWT = t.value;
    }

    protected String getS3Endpoint() {
        String u = this.URL;
        if (u.endsWith("/")) {
            u = u.substring(0, u.length() - 1);
        }
        return u;
    }

    private ApiClient getApiClient() {
        ApiClient apiClient = new ApiClient();
        apiClient.setBasePath(apiURL);
        if (this.serverNode.isSSLUnverified()) {
            SSLContext context = this.serverNode.getSslContext();
            OkHttpClient c = apiClient.getHttpClient();
            c.setSslSocketFactory(context.getSocketFactory());
            c.setHostnameVerifier((s, sslSession) -> URL.contains(s));
        }
        return apiClient;
    }

    protected String fullPath(String ws, String file) {
        String fullPath = "";
        fullPath += ws;
        return (fullPath + file).replace("//", "/");
    }

    private FileNode toFileNode(String ws, TreeNode node) {
        FileNode result = new FileNode();

        Map<String, String> meta = node.getMetaStore();
        result.setProperty(Pydio.NODE_PROPERTY_META_JSON_ENCODED, new JSONObject(meta).toString());
        boolean isFile = node.getType() == TreeNodeType.LEAF;
        String isImage = meta.get("is_image") == null ? "false" : meta.get("is_image");
        String ws_shares = meta.get("workspaces_shares");
        if (ws_shares != null) {
            result.setProperty(Pydio.NODE_PROPERTY_AJXP_SHARED, "true");
            result.setProperty(Pydio.NODE_PROPERTY_SHARE_JSON_INFO, ws_shares);
            try {
                JSONArray shareWorkspaces = new JSONArray(ws_shares);
                JSONObject shareWs = (JSONObject) shareWorkspaces.get(0);
                String shareUUID = shareWs.getString("UUID");
                result.setProperty(Pydio.NODE_PROPERTY_SHARE_UUID, shareUUID);
            } catch (ParseException ignored) {
            }
        }
        String uuid = node.getUuid();
        if (uuid == null) {
            return null;
        }
        result.setProperty(Pydio.NODE_PROPERTY_UUID, node.getUuid());
        result.setProperty(Pydio.NODE_PROPERTY_TEXT, new File(node.getPath()).getName());
        result.setProperty(Pydio.NODE_PROPERTY_LABEL, new File(node.getPath()).getName());
        String nodeSize = node.getSize();
        if (nodeSize == null) {
            if (!isFile) {
                result.setProperty(Pydio.NODE_PROPERTY_BYTESIZE, "4096");
            }
        } else {
            result.setProperty(Pydio.NODE_PROPERTY_BYTESIZE, node.getSize());
        }
        result.setProperty(Pydio.NODE_PROPERTY_PATH, node.getPath().replaceFirst(ws, ""));
        result.setProperty(Pydio.NODE_PROPERTY_FILENAME, node.getPath().replaceFirst(ws, ""));
        result.setProperty(Pydio.NODE_PROPERTY_IS_FILE, String.valueOf(isFile));
        result.setProperty(Pydio.NODE_PROPERTY_IS_IMAGE, isImage);
        result.setProperty(Pydio.NODE_PROPERTY_FILE_PERMS, String.valueOf(node.getMode()));
        result.setProperty(Pydio.NODE_PROPERTY_AJXP_MODIFTIME, node.getMtime());
        if (isImage.equals("true")) {
            result.setProperty(Pydio.NODE_PROPERTY_IMAGE_HEIGHT, meta.get("image_height"));
            result.setProperty(Pydio.NODE_PROPERTY_IMAGE_WIDTH, meta.get("image_height"));
            try {
                JSONObject thumb = new JSONObject(meta.get("ImageThumbnails"));
                boolean processing = thumb.getBoolean("Processing");
                if (!processing) {
                    JSONArray details = thumb.getJSONArray("thumbnails");
                    JSONObject thumbObject = new JSONObject();
                    for (int i = 0; i < details.length(); i++) {
                        JSONObject item = (JSONObject) details.get(i);
                        int size = item.getInt("size");
                        String format = item.getString("format");
                        String thumbPath = "/" + node.getUuid() + "-" + size + "." + format;
                        thumbObject.put("" + size, thumbPath);
                    }
                    result.setProperty(Pydio.NODE_PROPERTY_IMAGE_THUMB_PATHS, thumbObject.toString());
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        String encoded = new Gson().toJson(node);
        result.setProperty(Pydio.NODE_PROPERTY_ENCODED, encoded);
        result.setProperty(Pydio.NODE_PROPERTY_ENCODING, "gson");
        return result;
    }

    @Override
    public ServerNode getServerNode() {
        return serverNode;
    }

    @Override
    public void setCredentials(Credentials c) {
        this.credentials = c;
    }

    @Override
    public void setTokenProvider(Token.Provider p) {
        this.tokenProvider = p;
    }

    @Override
    public void setTokenStore(Token.Store s) {
        this.tokenStore = s;
    }

    @Override
    public String getUser() {
        return this.credentials.getLogin();
    }

    @Override
    public InputStream getUserData(String binary) {
        return null;
    }

    @Override
    public void login() throws SDKException {
        this.getJWT();
    }

    @Override
    public void logout() throws SDKException {
        RestFrontSessionRequest request = new RestFrontSessionRequest();
        request.setLogout(true);
        try {
            new FrontendServiceApi(getApiClient()).frontSession(request);
        } catch (ApiException e) {
            throw new SDKException(e);
        }
    }

    @Override
    public X509Certificate[] remoteCertificateChain() {
        return new X509Certificate[0];
    }

    @Override
    public void downloadServerRegistry(RegistryItemHandler itemHandler) throws SDKException {
        String fullURI = this.URL + "a/frontend/state/?ws=login";
        URL url = null;
        try {
            url = new URL(fullURI);
        } catch (MalformedURLException e) {
            throw SDKException.malFormURI(e);
        }

        HttpURLConnection con;
        InputStream in;
        try {
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            in = con.getInputStream();
        } catch (IOException e) {
            throw SDKException.conFailed(e);
        }

        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(in, new ServerGeneralRegistrySaxHandler(itemHandler));
        } catch (Exception e) {
            throw SDKException.unexpectedContent(e);
        } finally {
            try {
                in.close();
            } catch (IOException ignore) {
            }
        }
    }

    @Override
    public void downloadWorkspaceRegistry(String ws, RegistryItemHandler itemHandler) throws SDKException {

        String fullURI = this.URL + "/a/frontend/state/?ws=" + ws;
        URL url;
        try {
            url = new URL(fullURI);
        } catch (MalformedURLException e) {
            throw SDKException.malFormURI(e);
        }

        this.getJWT();

        HttpURLConnection con;
        InputStream in;
        try {
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
            con.setRequestProperty("Authorization", "Bearer " + this.JWT);
            in = con.getInputStream();

        } catch (IOException e) {
            throw SDKException.conFailed(e);
        }


        SAXParserFactory factory = SAXParserFactory.newInstance();
        try {
            SAXParser parser = factory.newSAXParser();
            parser.parse(in, new RegistrySaxHandler(itemHandler));
        } catch (Exception e) {
            e.printStackTrace();
            throw SDKException.unexpectedContent(e);
        } finally {
            io.close(in);
        }
    }

    @Override
    public void workspaceList(NodeHandler handler) throws SDKException {
        URL url = null;
        try {
            url = new URL(this.URL + "/a/frontend/state");
        } catch (MalformedURLException e) {
            throw SDKException.malFormURI(e);
        }

        this.getJWT();
        HttpURLConnection con = null;
        try {
            con = (HttpURLConnection) url.openConnection();
            con.setRequestMethod("GET");
        } catch (IOException e) {
            throw SDKException.conFailed(e);
        }

        con.setRequestProperty("Authorization", "Bearer " + this.JWT);
        InputStream in;
        try {
            in = con.getInputStream();
        } catch (IOException e) {
            e.printStackTrace();
            throw SDKException.conFailed(e);
        }

        try {
            DefaultHandler saxHandler = new WorkspaceNodeSaxHandler(handler, 0, -1);
            SAXParserFactory.newInstance().newSAXParser().parse(in, saxHandler);
        } catch (Exception e) {
            throw SDKException.unexpectedContent(e);
        }
    }

    @Override
    public FileNode nodeInfo(String ws, String path) throws SDKException {
        RestGetBulkMetaRequest request = new RestGetBulkMetaRequest();
        request.setAllMetaProviders(true);
        request.addNodePathsItem(fullPath(ws, path));
        RestBulkMetaResponse response;

        this.getJWT();
        ApiClient client = getApiClient();
        client.addDefaultHeader("Authorization", "Bearer " + this.JWT);
        TreeServiceApi api = new TreeServiceApi(client);
        try {
            response = api.bulkStatNodes(request);
        } catch (ApiException e) {
            throw new SDKException(e);
        }

        TreeNode node = response.getNodes().get(0);
        return toFileNode(ws, node);
    }

    @Override
    public FileNode ls(String ws, String folder, NodeHandler handler) throws SDKException {
        RestGetBulkMetaRequest request = new RestGetBulkMetaRequest();
        //request.addNodePathsItem(fullPath(ws, folder));
        if ("/".equals(folder)) {
            request.addNodePathsItem(ws + "/*");
        } else {
            request.addNodePathsItem(fullPath(ws, folder + "/*"));
        }

        request.setAllMetaProviders(true);

        this.getJWT();
        ApiClient client = getApiClient();
        client.addDefaultHeader("Authorization", "Bearer " + this.JWT);
        TreeServiceApi api = new TreeServiceApi(client);
        RestBulkMetaResponse response;
        try {
            response = api.bulkStatNodes(request);
        } catch (ApiException e) {
            throw new SDKException(e);
        }

        FileNode result = null;
        List<TreeNode> nodes = response.getNodes();
        if (nodes != null) {
            for (TreeNode node : response.getNodes()) {
                FileNode fileNode;
                try {
                    fileNode = toFileNode(ws, node);
                } catch (NullPointerException ignored) {
                    continue;
                }

                if (fileNode != null) {
                    String nodePath = ("/" + node.getPath()).replace("//", "/");
                    if (nodePath.equals(fullPath(ws, folder))) {
                        result = fileNode;
                    } else if (!fileNode.label().startsWith(".")) {
                        handler.onNode(fileNode);
                    }
                }
            }
        }
        return result;
    }

    @Override
    public void search(String ws, String pattern, NodeHandler h) {}

    @Override
    public Message upload(InputStream source, long length, String ws, String path, String name, boolean autoRename, TransferProgressListener progressListener) throws SDKException {
        /*try {
            MinioClient minioClient = new MinioClient(this.URL, this.JWT, "gatewaysecret");
            minioClient.putObject("io", name, source, length, new HashMap<>());
        } catch(MinioException e) {
            System.out.println("Error occurred: " + e);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return null;
    }

    @Override
    public Message upload(File source, String ws, String path, String name, boolean autoRename, TransferProgressListener progressListener) throws SDKException {
        return null;
    }

    @Override
    public String uploadURL(String ws, String folder, String name, boolean autoRename) throws SDKException {
        /*try {
            MinioClient minioClient = new MinioClient(this.URL, this.JWT, "gatewaysecret");
            return minioClient.getPresignedObjectUrl(Method.PUT, "io", "", 0,  null);
        } catch(Exception e) {
            System.out.println("Error occurred: " + e);
        }*/
        return null;
    }

    @Override
    public long download(String ws, String file, OutputStream target, TransferProgressListener progressListener) throws SDKException {
        /*try {
            MinioClient minioClient = new MinioClient(this.URL, this.JWT, "gatewaysecret");
            InputStream in = minioClient.getObject("io", fullPath(ws, file));
            if (progressListener != null) {
                return io.pipeReadWithProgress(in, target, progressListener::onProgress);
            } else {
                return io.pipeRead(in, target);
            }
        } catch(MinioException e) {
            System.out.println("Error occurred: " + e);
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (XmlPullParserException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }*/
        return 0;
    }

    @Override
    public long download(String ws, String file, File target, TransferProgressListener progressListener) throws SDKException {
        OutputStream out;
        try {
            out = new FileOutputStream(file);
        } catch (FileNotFoundException e) {
            throw SDKException.notFound(e);
        }

        long downloaded = download(ws, file, out, progressListener);
        io.close(out);
        return downloaded;
    }

    @Override
    public String downloadURL(String ws, String file) throws SDKException {
        /*try {
            MinioClient minioClient = new MinioClient(this.URL, this.JWT, "gatewaysecret");
            return minioClient.presignedGetObject("io", "", 0, null);
        } catch(Exception e) {
            System.out.println("Error occurred: " + e);
        }*/
        return null;
    }

    @Override
    public Message delete(String ws, String[] files) throws SDKException {
        List<TreeNode> nodes = new ArrayList<>();
        for (String file : files) {
            TreeNode node = new TreeNode();
            node.setPath(fullPath(ws, file));
            nodes.add(node);
        }

        RestDeleteNodesRequest request = new RestDeleteNodesRequest();
        request.setNodes(nodes);

        this.getJWT();

        ApiClient client = getApiClient();
        client.addDefaultHeader("Authorization", "Bearer " + this.JWT);
        TreeServiceApi api = new TreeServiceApi(client);

        try {
            api.deleteNodes(request);
        } catch (ApiException e) {
            e.printStackTrace();
            throw new SDKException(e);
        }
        return null;
    }

    @Override
    public Message restore(String ws, String[] files) throws SDKException {
        List<TreeNode> nodes = new ArrayList<>();
        for (String file : files) {
            TreeNode node = new TreeNode();
            node.setPath(fullPath(ws, file));
            nodes.add(node);
        }

        RestRestoreNodesRequest request = new RestRestoreNodesRequest();
        request.setNodes(nodes);

        this.getJWT();

        ApiClient client = getApiClient();
        client.addDefaultHeader("Authorization", "Bearer " + this.JWT);
        TreeServiceApi api = new TreeServiceApi(client);

        try {
            api.restoreNodes(request);
        } catch (ApiException e) {
            e.printStackTrace();
            throw new SDKException(e);
        }
        return null;
    }

    @Override
    public Message rename(String ws, String srcFile, String newName) throws SDKException {
        RestUserJobRequest request = new RestUserJobRequest();

        JSONArray nodes = new JSONArray();
        String path = fullPath(ws, srcFile);
        nodes.put(path);

        String parent = new File(srcFile).getParentFile().getPath();
        String dstFile;
        if ("/".equals(parent)) {
            dstFile = parent + newName;
        } else {
            dstFile = parent + "/" + newName;
        }

        JSONObject o = new JSONObject();
        o.put("nodes", nodes);
        o.put("target", fullPath(ws, dstFile));
        o.put("targetParent", false);

        request.setJobName("move");
        request.setJsonParameters(o.toString());

        this.getJWT();
        ApiClient client = getApiClient();
        client.addDefaultHeader("Authorization", "Bearer " + this.JWT);
        JobsServiceApi api = new JobsServiceApi(client);
        try {
            api.userCreateJob("move", request);
        } catch (ApiException e) {
            e.printStackTrace();
            throw new SDKException(e);
        }
        return null;
    }

    @Override
    public Message move(String ws, String[] files, String dstFolder) throws SDKException {
        JSONArray nodes = new JSONArray();
        for (String file : files) {
            String path = "/" + ws + file;
            nodes.put(path);
        }

        JSONObject o = new JSONObject();
        o.put("nodes", nodes);
        o.put("target", "/" + ws + dstFolder);
        o.put("targetParent", true);

        RestUserJobRequest request = new RestUserJobRequest();
        request.setJobName("move");
        request.setJsonParameters(o.toString());

        this.getJWT();
        ApiClient client = getApiClient();
        client.addDefaultHeader("Authorization", "Bearer " + this.JWT);
        JobsServiceApi api = new JobsServiceApi(client);
        try {
            api.userCreateJob("move", request);
        } catch (ApiException e) {
            e.printStackTrace();
            throw new SDKException(e);
        }
        return null;
    }

    @Override
    public Message copy(String ws, String[] files, String folder) throws SDKException {
        JSONArray nodes = new JSONArray();
        for (String file : files) {
            String path = "/" + ws + file;
            nodes.put(path);
        }

        JSONObject o = new JSONObject();
        o.put("nodes", nodes);
        o.put("target", "/" + ws + folder);
        o.put("targetParent", true);

        RestUserJobRequest request = new RestUserJobRequest();
        request.setJobName("copy");
        request.setJsonParameters(o.toString());


        this.getJWT();
        ApiClient client = getApiClient();
        client.addDefaultHeader("Authorization", "Bearer " + this.JWT);
        JobsServiceApi api = new JobsServiceApi(client);
        try {
            api.userCreateJob("copy", request);
        } catch (ApiException e) {
            e.printStackTrace();
            throw new SDKException(e);
        }
        return null;
    }

    @Override
    public Message mkdir(String ws, String parent, String name) throws SDKException {
        TreeNode node = new TreeNode();
        node.setPath((ws + parent + "/" + name).replace("//", "/"));
        node.setType(TreeNodeType.COLLECTION);

        RestCreateNodesRequest request = new RestCreateNodesRequest();
        request.recursive(false);
        request.addNodesItem(node);


        this.getJWT();
        ApiClient client = getApiClient();
        client.addDefaultHeader("Authorization", "Bearer " + this.JWT);
        TreeServiceApi api = new TreeServiceApi(client);

        RestNodesCollection response;
        try {
            response = api.createNodes(request);
        } catch (ApiException e) {
            e.printStackTrace();
            throw new SDKException(e);
        }

        Message msg = new Message();
        msg.added = new ArrayList<>();

        List<TreeNode> nodes = response.getChildren();
        node = nodes.get(0);

        FileNode fileNode = toFileNode(ws, node);
        msg.added.add(fileNode);
        return msg;
    }

    //@Override
    public Message mkfile(String ws, String name, String folder) throws SDKException {

        TreeNode node = new TreeNode();
        node.setPath("/" + ws + folder + "/" + name);
        node.setType(TreeNodeType.LEAF);

        RestCreateNodesRequest request = new RestCreateNodesRequest();
        request.recursive(false);
        request.addNodesItem(node);

        this.getJWT();
        ApiClient client = getApiClient();
        client.addDefaultHeader("Authorization", "Bearer " + this.JWT);
        TreeServiceApi api = new TreeServiceApi(client);

        RestNodesCollection response;
        try {
            response = api.createNodes(request);
        } catch (ApiException e) {
            e.printStackTrace();
            throw new SDKException(e);
        }

        Message msg = new Message();
        msg.added = new ArrayList<>();

        List<TreeNode> nodes = response.getChildren();
        node = nodes.get(0);

        FileNode fileNode = toFileNode(ws, node);
        msg.added.add(fileNode);
        return msg;
    }

    @Override
    public InputStream previewData(String ws, String file, int dim) throws SDKException {
        return null;
    }

    @Override
    public String streamingAudioURL(String ws, String file) {
        return null;
    }

    @Override
    public String streamingVideoURL(String ws, String file) {
        return null;
    }

    @Override
    public Stats stats(String ws, String file, boolean withHash) throws SDKException {
        RestGetBulkMetaRequest request = new RestGetBulkMetaRequest();
        request.addNodePathsItem(fullPath(ws, file));
        request.setAllMetaProviders(true);

        this.getJWT();
        ApiClient client = getApiClient();
        client.addDefaultHeader("Authorization", "Bearer " + this.JWT);
        TreeServiceApi api = new TreeServiceApi(client);
        RestBulkMetaResponse response;
        try {
            response = api.bulkStatNodes(request);
        } catch (ApiException e) {
            e.printStackTrace();
            throw new SDKException(e);
        }

        Stats stats = null;
        if (response.getNodes() != null) {
            TreeNode node = response.getNodes().get(0);
            stats = new Stats();
            stats.setHash(node.getEtag());
            stats.setSize(Long.parseLong(node.getSize()));
            stats.setmTime(Long.parseLong(node.getMtime()));
        }
        return stats;
    }

    @Override
    public long changes(String ws, String folder, int seq, boolean flatten, ChangeHandler cp) throws SDKException {
        RestChangeRequest request = new RestChangeRequest();
        request.setFlatten(flatten);
        request.setSeqID(String.valueOf(seq));
        request.setFilter("/" + ws + folder);


        this.getJWT();
        ApiClient client = getApiClient();
        client.addDefaultHeader("Authorization", "Bearer " + this.JWT);
        ChangeServiceApi api = new ChangeServiceApi(client);
        RestChangeCollection response;

        try {
            response = api.getChanges(String.valueOf(seq), request);
        } catch (ApiException e) {
            throw new SDKException(e);
        }

        for (TreeSyncChange c : response.getChanges()) {
            Change change = new Change();
            change.setSeq(Long.parseLong(c.getSeq()));
            change.setNodeId(c.getNodeId());
            change.setType(c.getType().toString());
            change.setSource(c.getSource());
            change.setTarget(c.getTarget());

            ChangeNode node = new ChangeNode();
            change.setNode(node);

            node.setSize(Long.parseLong(c.getNode().getBytesize()));
            node.setMd5(c.getNode().getMd5());
            node.setPath(c.getNode().getNodePath().replaceFirst("/" + ws, ""));
            node.setWorkspace(ws);
            node.setmTime(Long.parseLong(c.getNode().getMtime()));

            cp.onChange(change);
        }
        return Long.parseLong(response.getLastSeqId());
    }

    @Override
    public String share(String ws, String file, String ws_label, boolean isFolder, String ws_description, String password, int expiration, int download, boolean canPreview, boolean canDownload) throws SDKException {
        RestPutShareLinkRequest request = new RestPutShareLinkRequest();
        request.createPassword(password);
        request.setCreatePassword(password);
        request.setPasswordEnabled(Boolean.parseBoolean(password));
        RestShareLink sl = new RestShareLink();

        TreeNode n = new TreeNode();
        n.setUuid(file);

        List<RestShareLinkAccessType> permissions = new ArrayList<>();
        if (canPreview) {
            permissions.add(RestShareLinkAccessType.PREVIEW);
        }
        if (canDownload) {
            permissions.add(RestShareLinkAccessType.DOWNLOAD);
        }

        List<TreeNode> rootNodes = new ArrayList<>();
        rootNodes.add(n);
        sl.setPoliciesContextEditable(true);

        sl.setPermissions(permissions);
        sl.setRootNodes(rootNodes);
        sl.setPoliciesContextEditable(true);
        sl.setDescription(ws_description);
        sl.setLabel(ws_label);
        sl.setViewTemplateName("pydio_unique_strip");
        request.setShareLink(sl);

        this.getJWT();
        ApiClient client = getApiClient();
        client.addDefaultHeader("Authorization", "Bearer " + this.JWT);
        ShareServiceApi api = new ShareServiceApi(client);

        try {
            RestShareLink link = api.putShareLink(request);
            return link.getLinkUrl();
        } catch (ApiException e) {
            throw new SDKException(e);
        }
    }

    @Override
    public void unshare(String ws, String file) throws SDKException {
        this.getJWT();
        ApiClient client = getApiClient();
        client.addDefaultHeader("Authorization", "Bearer " + this.JWT);
        ShareServiceApi api = new ShareServiceApi(client);
        try {
            api.deleteShareLink(file);
        } catch (ApiException e) {
            throw new SDKException(e);
        }
    }

    @Override
    public JSONObject shareInfo(String ws, String shareID) throws SDKException {
        ApiClient client = getApiClient();
        client.addDefaultHeader("Authorization", "Bearer " + this.JWT);
        ShareServiceApi api = new ShareServiceApi(client);
        try {
            RestShareLink link = api.getShareLink(shareID);
            Gson gs = new Gson();
            String jsonString = gs.toJson(link);
            return new JSONObject(jsonString);
        } catch (ApiException e) {
            throw new SDKException(e);
        } catch (ParseException e) {
            throw SDKException.unexpectedContent(e);
        }
    }

    @Override
    public InputStream getCaptcha() {
        return null;
    }

    public interface Factory {
        PydioCells get(ServerNode node);
    }

    private static Map<String, Factory> registry = new HashMap<>();

    public static void registerFactory(String name, Factory f) {
        registry.put(name, f);
    }

    public static void registerFactory(Factory f) {
        registry.put("default", f);
    }

    public static Factory getFactory(String name) {
        if (registry.containsKey(name)) {
            return registry.get(name);
        }
        return registry.get("default");
    }

    public static Factory getFactory() {
        return registry.get("default");
    }

    @Override
    public JSONObject authenticationInfo() {
        return null;
    }

    @Override
    public Message emptyRecycleBin(String ws) throws SDKException {
        return delete(ws, new String[]{"/recycle_bin"});
    }
}
