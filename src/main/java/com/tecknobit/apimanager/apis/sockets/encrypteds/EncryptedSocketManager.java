package com.tecknobit.apimanager.apis.sockets.encrypteds;

import com.tecknobit.apimanager.apis.encryption.BaseCipher;
import com.tecknobit.apimanager.apis.sockets.SocketManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

public abstract class EncryptedSocketManager<T extends BaseCipher> extends SocketManager {

    protected T cipher;

    /**
     * Constructor to init {@link SocketManager}
     *
     * @param host       :       server host used in the communication
     * @param serverPort : server port used in the communication
     * @param cipher     :     cipher used to cipher the communication
     * @apiNote this will set {@link #serverUse} to {@code "false"} and will be used as client side
     */
    public EncryptedSocketManager(String host, int serverPort, T cipher) {
        super(host, serverPort);
        this.cipher = cipher;
    }

    /**
     * Constructor to init {@link SocketManager}
     *
     * @param allowMultipleListeners : whether accept multiple listeners at the same time
     * @param cipher                 :                 cipher used to cipher the communication
     * @throws UnknownHostException when an error occurred
     * @apiNote this will set {@link #serverUse} to {@code "true"} and will be used as server side
     */
    public EncryptedSocketManager(boolean allowMultipleListeners, T cipher) throws UnknownHostException {
        super(allowMultipleListeners);
        this.cipher = cipher;
    }

    @Override
    public <V> void writeContentTo(Socket targetSocket, V content) throws Exception {
        String message = content.toString();
        if (message.contains(NEW_LINE_REPLACER)) {
            socket.close();
            exit("\"@-/-/-@\" is a reserved char, please do not insert it");
        }
        writePlainContentTo(targetSocket, cipher.encryptBase64(message.replaceAll("\n", NEW_LINE_REPLACER)));
    }

    @Override
    public String readContent(Socket targetSocket) throws Exception {
        String content = cipher.decryptBase64(new BufferedReader(new InputStreamReader(targetSocket.getInputStream()))
                .readLine());
        lastContentRed = content;
        if (!serverUse || content == null)
            targetSocket.close();
        if (content != null)
            content = content.replaceAll(NEW_LINE_REPLACER, "\n");
        return content;
    }

    @Override
    public String readLastContent() throws Exception {
        return cipher.decryptBase64(super.readLastContent());
    }

    /**
     * Method to set the {@link #cipher} instance
     *
     * @param cipher: cipher to use
     * @apiNote this will enable the auto-cipher of the communication
     */
    public void setCipher(T cipher) {
        this.cipher = cipher;
    }

    /**
     * Method to get {@link #cipher} instance <br>
     * No-any params required
     *
     * @return {@link #cipher} instance as {@link T}
     */
    public T getCipher() {
        return cipher;
    }

}
