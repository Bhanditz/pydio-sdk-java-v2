package pydio.sdk.java;

import org.json.JSONObject;

import java.io.File;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.cert.X509Certificate;

import pydio.sdk.java.core.errors.SDKException;
import pydio.sdk.java.core.model.FileNode;
import pydio.sdk.java.core.model.Message;
import pydio.sdk.java.core.model.ServerNode;
import pydio.sdk.java.core.utils.ChangeProcessor;
import pydio.sdk.java.core.utils.NodeHandler;
import pydio.sdk.java.core.utils.RegistryItemHandler;
import pydio.sdk.java.core.utils.Token;
import pydio.sdk.java.core.utils.TransferProgressListener;

public interface Client {

    static Client get(ServerNode node) {
        if (node.versionName().startsWith("cells")) {
            return PydioCells.getFactory().get(node);
        }
        return new Pydio8(node);
    }

    String getURLString();

    void setUser(String user);

    void setTokenProvider(Token.Provider p);

    void setTokenStore(Token.Store s);

    void setServerNode(ServerNode node);

    String getUser();

    InputStream getUserData(String binary) throws SDKException;

    void login() throws SDKException;

    void logout() throws SDKException;

    X509Certificate[] remoteCertificateChain();

    void downloadServerRegistry(RegistryItemHandler itemHandler) throws SDKException;

    void downloadWorkspaceRegistry(String ws, RegistryItemHandler itemHandler) throws SDKException;

    void workspaceList(final NodeHandler handler) throws SDKException;

    FileNode nodeInfo(String ws, String path) throws SDKException;

    FileNode ls(String ws, String folder, NodeHandler handler) throws SDKException;

    void search(String ws, String pattern, NodeHandler h) throws SDKException;

    Message upload(InputStream source, long length, String ws, String path, String name, boolean autoRename, final TransferProgressListener progressListener) throws SDKException;

    Message upload(File source, String ws, String path, String name, boolean autoRename, final TransferProgressListener progressListener) throws SDKException;

    String uploadURL(String ws, String folder, String name, boolean autoRename) throws SDKException;

    long download(String ws, String file, OutputStream target, TransferProgressListener progressListener) throws SDKException;

    long download(String ws, String file, File target, TransferProgressListener progressListener) throws SDKException;

    String downloadURL(String ws, String file) throws SDKException;

    Message delete(String ws, String[] files) throws SDKException;

    Message restore(String ws, String[] files) throws SDKException;

    Message move(String ws, String[] files, String dstFolder) throws SDKException;

    Message rename(String ws, String srcFile, String newName) throws SDKException;

    Message copy(String ws, String[] files, String folder) throws SDKException;

    Message mkdir(String ws, String parent, String name) throws SDKException;

    InputStream previewData(String ws, String file) throws SDKException;

    String streamingAudioURL(String ws, String file) throws SDKException;

    String streamingVideoURL(String ws, String file) throws SDKException;

    JSONObject stats(String ws, String file, boolean withHash) throws SDKException;

    long changes(String ws, String folder, int seq, boolean flatten, ChangeProcessor processor) throws SDKException;

    String share(String ws, String file, String ws_label, boolean isFolder, String ws_description, String password, int expiration, int download, boolean canPreview, boolean canDownload) throws SDKException;

    void unshare(String ws, String file) throws SDKException;

    JSONObject shareInfo(String ws, String file) throws SDKException;

    InputStream getCaptcha() throws SDKException;
}
