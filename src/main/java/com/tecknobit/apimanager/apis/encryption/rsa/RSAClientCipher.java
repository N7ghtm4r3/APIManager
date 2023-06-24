package com.tecknobit.apimanager.apis.encryption.rsa;

import com.tecknobit.apimanager.apis.encryption.BaseCipher;

import java.security.*;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;

import static com.tecknobit.apimanager.apis.encryption.BaseCipher.Algorithm.RSA_ALGORITHM;
import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;

/**
 * The {@code RSAClientCipher} class is useful for {@code "RSA"} encryption
 *
 * @author Tecknobit N7ghtm4r3
 * @apiNote see the usage at <a href="https://github.com/N7ghtm4r3/APIManager/blob/main/documd/RSA.md">RSA.md</a>
 * @see BaseCipher
 * @since 2.1.5
 */
public class RSAClientCipher extends BaseCipher {

    /**
     * {@code privateKey} the private key used to decrypt the content
     */
    protected PrivateKey privateKey;

    /**
     * {@code publicKey} the public key used to encrypt the content
     */
    protected PublicKey publicKey;

    /**
     * Constructor to init a {@link RSAClientCipher} object
     *
     * @param privateKey: the private key used to decrypt the content
     * @param publicKey:  the public key used to encrypt the content
     */
    public RSAClientCipher(String privateKey, String publicKey) throws Exception {
        super(RSA_ALGORITHM);
        this.privateKey = recreateKey(privateKey, true);
        this.publicKey = recreateKey(publicKey, false);
    }

    /**
     * Constructor to init a {@link RSAClientCipher} object
     *
     * @param keyPair: key pair from fetch the private and the public key
     */
    public RSAClientCipher(KeyPair keyPair) throws Exception {
        this(keyPair.getPrivate(), keyPair.getPublic());
    }

    /**
     * Constructor to init a {@link RSAClientCipher} object
     *
     * @param privateKey: the private key used to decrypt the content
     * @param publicKey:  the public key used to encrypt the content
     */
    public RSAClientCipher(PrivateKey privateKey, PublicKey publicKey) throws Exception {
        super(RSA_ALGORITHM);
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    /**
     * Method to encrypt any content
     *
     * @param content: content to encrypt
     * @return content encrypted as array of byte
     */
    @Override
    public <T> byte[] encrypt(T content) throws Exception {
        cipher.init(ENCRYPT_MODE, publicKey);
        return super.encrypt(content);
    }

    /**
     * Method to decrypt a content
     *
     * @param content: content to decrypt
     * @return content decrypted as array of byte
     */
    @Override
    public byte[] decrypt(byte[] content) throws Exception {
        cipher.init(DECRYPT_MODE, privateKey);
        return super.decrypt(content);
    }

    /**
     * Method to get {@link #privateKey} instance <br>
     * No-any params required
     *
     * @return {@link #privateKey} instance as {@link PrivateKey}
     */
    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    /**
     * Method to get {@link #privateKey} instance <br>
     * No-any params required
     *
     * @return {@link #privateKey} instance as {@link Base64}-{@link String}
     */
    public String getBase64PrivateKey() {
        return encodeBase64(privateKey.getEncoded());
    }

    /**
     * Method to set {@link #privateKey} instance
     *
     * @param privateKey: the private key used to decrypt the content
     */
    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    /**
     * Method to set {@link #privateKey} instance
     *
     * @param privateKey: the private key used to decrypt the content
     */
    public void setPrivateKey(String privateKey) throws Exception {
        this.privateKey = recreateKey(privateKey, true);
    }

    /**
     * Method to get {@link #publicKey} instance <br>
     * No-any params required
     *
     * @return {@link #publicKey} instance as {@link PublicKey}
     */
    public PublicKey getPublicKey() {
        return publicKey;
    }

    /**
     * Method to get {@link #publicKey} instance <br>
     * No-any params required
     *
     * @return {@link #publicKey} instance as {@link Base64}-{@link String}
     */
    public String getBase64PublicKey() {
        return encodeBase64(publicKey.getEncoded());
    }

    /**
     * Method to set {@link #publicKey} instance
     *
     * @param publicKey: the public key used to decrypt the content
     */
    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

    /**
     * Method to set {@link #publicKey} instance
     *
     * @param publicKey: the public key used to decrypt the content
     */
    public void setPublicKey(String publicKey) throws Exception {
        this.publicKey = recreateKey(publicKey, false);
    }

    /**
     * Method to set the keys
     *
     * @param privateKey: the private key used to decrypt the content
     * @param publicKey:  the public key used to encrypt the content
     */
    public void setKeys(String privateKey, String publicKey) throws Exception {
        setKeys((PrivateKey) recreateKey(privateKey, true), recreateKey(publicKey, false));
    }

    /**
     * Method to set the keys
     *
     * @param keyPair: key pair from fetch the private and the public key
     */
    public void setKeys(KeyPair keyPair) {
        setKeys(keyPair.getPrivate(), keyPair.getPublic());
    }

    /**
     * Method to set the keys
     *
     * @param privateKey: the private key used to decrypt the content
     * @param publicKey:  the public key used to encrypt the content
     */
    public void setKeys(PrivateKey privateKey, PublicKey publicKey) {
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    /**
     * Method to recreate from a {@link String} source a key
     *
     * @param key:          the string source from recreate the RSA key
     * @param isPrivateKey: whether the key to recreate is a private key
     * @return the key recreated as {@link T}
     */
    public static <T extends Key> T recreateKey(String key, boolean isPrivateKey) throws Exception {
        byte[] keyBytes;
        try {
            keyBytes = decodeBase64(key);
        } catch (IllegalArgumentException e) {
            keyBytes = key.getBytes();
        }
        KeyFactory keyFactory = KeyFactory.getInstance(RSA_ALGORITHM.toString());
        if (isPrivateKey) {
            PKCS8EncodedKeySpec keySpecPKCS8 = new PKCS8EncodedKeySpec(keyBytes);
            return (T) keyFactory.generatePrivate(keySpecPKCS8);
        } else {
            X509EncodedKeySpec keySpec = new X509EncodedKeySpec(keyBytes);
            return (T) keyFactory.generatePublic(keySpec);
        }
    }

}
