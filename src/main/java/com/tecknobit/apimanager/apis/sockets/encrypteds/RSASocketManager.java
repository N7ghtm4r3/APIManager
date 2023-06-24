package com.tecknobit.apimanager.apis.sockets.encrypteds;

import com.tecknobit.apimanager.annotations.Wrapper;
import com.tecknobit.apimanager.apis.encryption.rsa.RSAClientCipher;
import com.tecknobit.apimanager.apis.sockets.SocketManager;

import java.security.KeyPair;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

import static com.tecknobit.apimanager.apis.encryption.rsa.RSAClientCipher.recreateKey;

/**
 * The {@code RSASocketManager} class is useful to dynamically manage communication by sockets with RSA encryption
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the usage at <a href="https://github.com/N7ghtm4r3/APIManager/blob/main/documd/SocketManager.md">SocketManager.md</a>
 * @see SocketManager
 * @see EncryptedSocketManager
 * @since 2.1.5
 */
public class RSASocketManager extends EncryptedSocketManager<RSAClientCipher> {

    /**
     * Constructor to init {@link RSASocketManager}
     *
     * @param host        :  server host used in the communication
     * @param serverPort  : server port used in the communication
     * @param privateKey: the private key used to decrypt the content
     * @param publicKey:  the public key used to encrypt the content
     * @apiNote this will set {@link #serverUse} to {@code "false"} and will be used as client side
     */
    public RSASocketManager(String host, int serverPort, String privateKey, String publicKey) throws Exception {
        super(host, serverPort, new RSAClientCipher(privateKey, publicKey));
    }

    /**
     * Constructor to init {@link RSASocketManager}
     *
     * @param host       :  server host used in the communication
     * @param serverPort : server port used in the communication
     * @param keyPair:   key pair from fetch the private and the public key
     * @apiNote this will set {@link #serverUse} to {@code "false"} and will be used as client side
     */
    public RSASocketManager(String host, int serverPort, KeyPair keyPair) throws Exception {
        super(host, serverPort, new RSAClientCipher(keyPair.getPrivate(), keyPair.getPublic()));
    }

    /**
     * Constructor to init {@link RSASocketManager}
     *
     * @param host        :  server host used in the communication
     * @param serverPort  : server port used in the communication
     * @param privateKey: the private key used to decrypt the content
     * @param publicKey:  the public key used to encrypt the content
     * @apiNote this will set {@link #serverUse} to {@code "false"} and will be used as client side
     */
    public RSASocketManager(String host, int serverPort, PrivateKey privateKey, PublicKey publicKey) throws Exception {
        super(host, serverPort, new RSAClientCipher(privateKey, publicKey));
    }

    /**
     * Constructor to init {@link RSASocketManager}
     *
     * @param host             :  server host used in the communication
     * @param serverPort       : server port used in the communication
     * @param rsaClientCipher: the RSA cipher used during the communication
     * @apiNote this will set {@link #serverUse} to {@code "false"} and will be used as client side
     */
    public RSASocketManager(String host, int serverPort, RSAClientCipher rsaClientCipher) {
        super(host, serverPort, rsaClientCipher);
    }

    /**
     * Constructor to init {@link RSASocketManager}
     *
     * @param allowMultipleListeners : whether accept multiple listeners at the same time
     * @param privateKey:            the private key used to decrypt the content
     * @param publicKey:             the public key used to encrypt the content
     * @apiNote this will set {@link #serverUse} to {@code "true"} and will be used as server side
     */
    public RSASocketManager(boolean allowMultipleListeners, String privateKey, String publicKey) throws Exception {
        super(allowMultipleListeners, new RSAClientCipher(privateKey, publicKey));
    }

    /**
     * Constructor to init {@link RSASocketManager}
     *
     * @param allowMultipleListeners : whether accept multiple listeners at the same time
     * @param keyPair:               key pair from fetch the private and the public key
     * @apiNote this will set {@link #serverUse} to {@code "true"} and will be used as server side
     */
    public RSASocketManager(boolean allowMultipleListeners, KeyPair keyPair) throws Exception {
        super(allowMultipleListeners, new RSAClientCipher(keyPair.getPrivate(), keyPair.getPublic()));
    }

    /**
     * Constructor to init {@link RSASocketManager}
     *
     * @param allowMultipleListeners : whether accept multiple listeners at the same time
     * @param privateKey:            the private key used to decrypt the content
     * @param publicKey:             the public key used to encrypt the content
     * @apiNote this will set {@link #serverUse} to {@code "true"} and will be used as server side
     */
    public RSASocketManager(boolean allowMultipleListeners, PrivateKey privateKey, PublicKey publicKey) throws Exception {
        super(allowMultipleListeners, new RSAClientCipher(privateKey, publicKey));
    }

    /**
     * Constructor to init {@link RSASocketManager}
     *
     * @param allowMultipleListeners : whether accept multiple listeners at the same time
     * @param rsaClientCipher:       the RSA cipher used during the communication
     * @apiNote this will set {@link #serverUse} to {@code "true"} and will be used as server side
     */
    public RSASocketManager(boolean allowMultipleListeners, RSAClientCipher rsaClientCipher) throws Exception {
        super(allowMultipleListeners, rsaClientCipher);
    }

    /**
     * Method to get the current private key used <br>
     * No-any params required
     *
     * @return the current private key used instance as {@link PrivateKey}
     */
    public PrivateKey getPrivateKey() {
        return cipher.getPrivateKey();
    }

    /**
     * Method to get the current private key used instance <br>
     * No-any params required
     *
     * @return current private key used instance as {@link Base64}-{@link String}
     */
    public String getBase64PrivateKey() {
        return cipher.getBase64PrivateKey();
    }

    /**
     * Method to get the current public key used<br>
     * No-any params required
     *
     * @return the current public key used as {@link PublicKey}
     */
    public PublicKey getPublicKey() {
        return cipher.getPublicKey();
    }

    /**
     * Method to get current public key used instance <br>
     * No-any params required
     *
     * @return current public key used instance as {@link Base64}-{@link String}
     */
    public String getBase64PublicKey() {
        return cipher.getBase64PublicKey();
    }

    /**
     * Method to change the cipher keys during the runtime
     *
     * @param privateKey: the private key used to decrypt the content
     * @param publicKey:  the public key used to encrypt the content
     * @throws Exception when an error occurred
     */
    @Wrapper
    public void changeCipherKeys(String privateKey, String publicKey) throws Exception {
        changeCipherKeys((PrivateKey) recreateKey(privateKey, true), recreateKey(publicKey, false));
    }

    /**
     * Method to change the cipher keys during the runtime
     *
     * @param keyPair: key pair from fetch the private and the public key
     */
    @Wrapper
    public void changeCipherKeys(KeyPair keyPair) {
        changeCipherKeys(keyPair.getPrivate(), keyPair.getPublic());
    }

    /**
     * Method to change the cipher keys during the runtime
     *
     * @param privateKey: the private key used to decrypt the content
     * @param publicKey:  the public key used to encrypt the content
     */
    public void changeCipherKeys(PrivateKey privateKey, PublicKey publicKey) {
        cipher.setKeys(privateKey, publicKey);
    }

    /**
     * Method to set the {@link #cipher} instance
     *
     * @param privateKey: the private key used to decrypt the content
     * @param publicKey:  the public key used to encrypt the content
     * @throws Exception when an error occurred
     */
    @Wrapper
    public void setCipher(String privateKey, String publicKey) throws Exception {
        setCipher((PrivateKey) recreateKey(privateKey, true), recreateKey(publicKey, false));
    }

    /**
     * Method to set the {@link #cipher} instance
     *
     * @param keyPair: key pair from fetch the private and the public key
     * @throws Exception when an error occurred
     */
    @Wrapper
    public void setCipher(KeyPair keyPair) throws Exception {
        setCipher(keyPair.getPrivate(), keyPair.getPublic());
    }

    /**
     * Method to set the {@link #cipher} instance
     *
     * @param privateKey: the private key used to decrypt the content
     * @param publicKey:  the public key used to encrypt the content
     * @throws Exception when an error occurred
     */
    public void setCipher(PrivateKey privateKey, PublicKey publicKey) throws Exception {
        setCipher(new RSAClientCipher(privateKey, publicKey));
    }

}
