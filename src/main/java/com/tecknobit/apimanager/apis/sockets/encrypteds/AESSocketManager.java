package com.tecknobit.apimanager.apis.sockets.encrypteds;

import com.tecknobit.apimanager.annotations.Wrapper;
import com.tecknobit.apimanager.apis.encryption.BaseCipher.Algorithm;
import com.tecknobit.apimanager.apis.encryption.aes.AESClientCipher;
import com.tecknobit.apimanager.apis.sockets.SocketManager;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import static com.tecknobit.apimanager.apis.encryption.aes.AESClientCipher.createIvParameter;
import static com.tecknobit.apimanager.apis.encryption.aes.AESClientCipher.createSecretKey;

/**
 * The {@code AESSocketManager} class is useful to dynamically manage communication by sockets with AES encryption
 *
 * @author N7ghtm4r3 - Tecknobit
 * @apiNote see the usage at <a href="https://github.com/N7ghtm4r3/APIManager/blob/main/documd/SocketManager.md">SocketManager.md</a>
 * @see SocketManager
 * @see EncryptedSocketManager
 * @since 2.1.5
 */
public class AESSocketManager extends EncryptedSocketManager<AESClientCipher> {

    /**
     * Constructor to init {@link AESSocketManager}
     *
     * @param host:       server host used in the communication
     * @param serverPort: server port used in the communication
     * @param ivSpec:     initialization vector as {@link String}
     * @param secretKey:  secret key used in the {@link Cipher} as {@link String}
     * @param algorithm:  algorithm used for AES cipher
     * @throws Exception when an error occurred
     * @apiNote this will set {@link #serverUse} to {@code "false"} and will be used as client side
     */
    public AESSocketManager(String host, int serverPort, String ivSpec, String secretKey,
                            Algorithm algorithm) throws Exception {
        super(host, serverPort, new AESClientCipher(ivSpec, secretKey, algorithm));
    }

    /**
     * Constructor to init {@link AESSocketManager}
     *
     * @param host:       server host used in the communication
     * @param serverPort: server port used in the communication
     * @param ivSpec:     initialization vector as {@link IvParameterSpec}
     * @param secretKey:  secret key used in the {@link Cipher} as {@link SecretKey}
     * @param algorithm:  algorithm used for AES cipher
     * @throws Exception when an error occurred
     * @apiNote this will set {@link #serverUse} to {@code "false"} and will be used as client side
     */
    public AESSocketManager(String host, int serverPort, IvParameterSpec ivSpec, SecretKey secretKey,
                            Algorithm algorithm) throws Exception {
        super(host, serverPort, new AESClientCipher(ivSpec, secretKey, algorithm));
    }

    /**
     * Constructor to init {@link AESSocketManager}
     *
     * @param host:            server host used in the communication
     * @param serverPort:      server port used in the communication
     * @param aesClientCipher: the AES cipher used during the communication
     * @apiNote this will set {@link #serverUse} to {@code "false"} and will be used as client side
     */
    public AESSocketManager(String host, int serverPort, AESClientCipher aesClientCipher) {
        super(host, serverPort, aesClientCipher);
    }

    /**
     * Constructor to init {@link AESSocketManager}
     *
     * @param allowMultipleListeners: whether accept multiple listeners at the same time
     * @param ivSpec:                 initialization vector as {@link String}
     * @param secretKey:              secret key used in the {@link Cipher} as {@link String}
     * @param algorithm:              algorithm used for AES cipher
     * @throws Exception when an error occurred
     * @apiNote this will set {@link #serverUse} to {@code "true"} and will be used as server side
     */
    public AESSocketManager(boolean allowMultipleListeners, String ivSpec, String secretKey,
                            Algorithm algorithm) throws Exception {
        super(allowMultipleListeners, new AESClientCipher(ivSpec, secretKey, algorithm));
    }

    /**
     * Constructor to init {@link AESSocketManager}
     *
     * @param allowMultipleListeners: whether accept multiple listeners at the same time
     * @param ivSpec:                 initialization vector as {@link IvParameterSpec}
     * @param secretKey:              secret key used in the {@link Cipher} as {@link SecretKey}
     * @param algorithm:              algorithm used for AES cipher
     * @throws Exception when an error occurred
     * @apiNote this will set {@link #serverUse} to {@code "true"} and will be used as server side
     */
    public AESSocketManager(boolean allowMultipleListeners, IvParameterSpec ivSpec, SecretKey secretKey,
                            Algorithm algorithm) throws Exception {
        super(allowMultipleListeners, new AESClientCipher(ivSpec, secretKey, algorithm));
    }

    /**
     * Constructor to init {@link AESSocketManager}
     *
     * @param allowMultipleListeners: whether accept multiple listeners at the same time
     * @param aesClientCipher:        the AES cipher used during the communication
     * @throws Exception when an error occurred
     * @apiNote this will set {@link #serverUse} to {@code "true"} and will be used as server side
     */
    public AESSocketManager(boolean allowMultipleListeners, AESClientCipher aesClientCipher) throws Exception {
        super(allowMultipleListeners, aesClientCipher);
    }

    /**
     * This method is used to get the initialization vector <br>
     * No-any params required
     *
     * @return initialization vector instance as {@link IvParameterSpec}
     */
    public IvParameterSpec getIvParameterSpec() {
        return cipher.getIvParameterSpec();
    }

    /**
     * This method is used to get the initialization vector <br>
     * No-any params required
     *
     * @return initialization vector instance as {@link String}
     */
    public String getBase64IvParameterSpec() {
        return cipher.getBase64IvParameterSpec();
    }

    /**
     * This method is used to get the secret key <br>
     * No-any params required
     *
     * @return the secret key as {@link SecretKey}
     */
    public SecretKey getSecretKey() {
        return cipher.getSecretKey();
    }

    /**
     * This method is used to get the secret key <br>
     * No-any params required
     *
     * @return the secret key as {@link String}
     */
    public String getBase64SecretKey() {
        return cipher.getBase64SecretKey();
    }

    /**
     * Method to change the cipher keys during the runtime
     *
     * @param ivSpec:    initialization vector as {@link String}
     * @param secretKey: secret key as {@link String}
     */
    @Wrapper
    public void changeCipherKeys(String ivSpec, String secretKey) {
        changeCipherKeys(createIvParameter(ivSpec), createSecretKey(secretKey));
    }

    /**
     * Method to change the cipher keys during the runtime
     *
     * @param ivSpec:    initialization vector as {@link String}
     * @param secretKey: secret key as {@link String}
     * @param algorithm: algorithm used by the {@link #cipher}
     */
    @Wrapper
    public void changeCipherKeys(String ivSpec, String secretKey, Algorithm algorithm) {
        changeCipherKeys(createIvParameter(ivSpec), createSecretKey(secretKey), algorithm);
    }

    /**
     * Method to change the cipher keys during the runtime
     *
     * @param ivSpec:    initialization vector as {@link IvParameterSpec}
     * @param secretKey: secret key as {@link SecretKey}
     */
    @Wrapper
    public void changeCipherKeys(IvParameterSpec ivSpec, SecretKey secretKey) {
        changeCipherKeys(ivSpec, secretKey, cipher.getAlgorithm());
    }

    /**
     * Method to change the cipher keys during the runtime
     *
     * @param ivSpec:    initialization vector as {@link IvParameterSpec}
     * @param secretKey: secret key as {@link SecretKey}
     * @param algorithm: algorithm used by the {@link #cipher}
     */
    public void changeCipherKeys(IvParameterSpec ivSpec, SecretKey secretKey, Algorithm algorithm) {
        cipher.setIvParameterSpec(ivSpec);
        cipher.setSecretKey(secretKey);
        cipher.setAlgorithm(algorithm);
    }

    /**
     * Method to set the {@link #cipher} instance
     *
     * @param ivSpec:    initialization vector as {@link String}
     * @param secretKey: secret key as {@link String}
     * @param algorithm: algorithm used by the {@link #cipher}
     * @throws Exception when an error occurred
     */
    @Wrapper
    public void setCipher(String ivSpec, String secretKey, Algorithm algorithm) throws Exception {
        setCipher(createIvParameter(ivSpec), createSecretKey(secretKey), algorithm);
    }

    /**
     * Method to set the {@link #cipher} instance
     *
     * @param ivSpec:    initialization vector as {@link IvParameterSpec}
     * @param secretKey: secret key as {@link SecretKey}
     * @param algorithm: algorithm used by the {@link #cipher}
     * @throws Exception when an error occurred
     */
    @Wrapper
    public void setCipher(IvParameterSpec ivSpec, SecretKey secretKey, Algorithm algorithm) throws Exception {
        setCipher(new AESClientCipher(ivSpec, secretKey, algorithm));
    }

}