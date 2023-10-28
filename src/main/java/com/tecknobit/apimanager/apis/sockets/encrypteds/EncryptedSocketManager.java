package com.tecknobit.apimanager.apis.sockets.encrypteds;

import com.tecknobit.apimanager.apis.encryption.BaseCipher;
import com.tecknobit.apimanager.apis.sockets.SocketManager;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.Socket;
import java.net.UnknownHostException;

/**
 * The {@code EncryptedSocketManager} class is useful to dynamically manage encrypted communication by sockets
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the usage at <a href="https://github.com/N7ghtm4r3/APIManager/blob/main/documd/SocketManager.md">SocketManager.md</a>
 * @since 2.1.5
 */
public abstract class EncryptedSocketManager<T extends BaseCipher> extends SocketManager {

    /**
     * {@code cipher} to manage the encryption or decryption during the communication
     */
    protected T cipher;

    /**
     * Constructor to init {@link EncryptedSocketManager}
     *
     * @param host       :  server host used in the communication
     * @param serverPort : server port used in the communication
     * @param cipher     : cipher to manage the encryption or decryption during the communication
     * @apiNote this will set {@link #serverUse} to {@code "false"} and will be used as client side
     */
    public EncryptedSocketManager(String host, int serverPort, T cipher) {
        super(host, serverPort);
        this.cipher = cipher;
    }

    /**
     * Constructor to init {@link EncryptedSocketManager}
     *
     * @param allowMultipleListeners : whether accept multiple listeners at the same time
     * @param cipher                 : cipher to manage the encryption or decryption during the communication
     * @throws UnknownHostException when an error occurred
     * @apiNote this will set {@link #serverUse} to {@code "true"} and will be used as server side
     */
    public EncryptedSocketManager(boolean allowMultipleListeners, T cipher) throws UnknownHostException {
        super(allowMultipleListeners);
        this.cipher = cipher;
    }

    /**
     * Method to write an encrypted content message to send with the socket request
     *
     * @param targetSocket: target socket to use to send the content message
     * @param content:      content message to send
     * @throws Exception when some errors have been occurred
     * @apiNote will be accepted any objects, but will be called their {@code "toString()"}'s method to be sent
     */
    @Override
    public <V> void writeContentTo(Socket targetSocket, V content) throws Exception {
        String message = content.toString();
        if (message.contains(NEW_LINE_REPLACER)) {
            socket.close();
            exit("\"@-/-/-@\" is a reserved char, please do not insert it");
        }

        writePlainContentTo(targetSocket, cipher.encryptBase64(message.replaceAll("\n", NEW_LINE_REPLACER)));
    }

    /**
     * Method to read an encrypted content message received with the socket request
     *
     * @param targetSocket: target socket to use to receive the content message
     * @return content message received as {@link String}
     * @throws Exception when some errors have been occurred
     */
    @Override
    public String readContent(Socket targetSocket) throws Exception {
        String content = new BufferedReader(new InputStreamReader(targetSocket.getInputStream())).readLine();
        try {
            content = cipher.decryptBase64(content);
        } catch (IllegalArgumentException e) {
            throw new IllegalArgumentException(e);
        } finally {
            lastContentRed = content;
        }
        if (!serverUse || content == null)
            targetSocket.close();
        if (content != null)
            content = content.replaceAll(NEW_LINE_REPLACER, "\n");
        return content;
    }

    /**
     * Method to read the last encrypted content message red in the stream with the socket request <br>
     * No-any params required
     *
     * @return last content message red as {@link String}
     * @throws Exception when some errors have been occurred
     */
    @Override
    public String readLastContent() throws Exception {
        try {
            return cipher.decryptBase64(lastContentRed);
        } catch (IllegalArgumentException e) {
            return lastContentRed;
        }
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
