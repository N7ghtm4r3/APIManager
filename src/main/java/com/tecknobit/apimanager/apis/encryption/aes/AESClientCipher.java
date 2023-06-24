package com.tecknobit.apimanager.apis.encryption.aes;

import com.tecknobit.apimanager.apis.encryption.BaseCipher;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;

/**
 * The {@code AESClientCipher} class is useful for {@code "AES"} encryption
 *
 * @author Tecknobit N7ghtm4r3
 * @apiNote see the usage at <a href="https://github.com/N7ghtm4r3/APIManager/blob/main/documd/AES.md">AES.md</a>
 * @see BaseCipher
 * @since 2.0.2
 */
public class AESClientCipher extends BaseCipher {

    /**
     * {@code AES_ALGORITHM_TYPE} is constant that memorizes algorithm type used for the {@link #cipher}
     */
    public static final String AES_ALGORITHM_TYPE = "AES";

    /**
     * {@code ivParameterSpec} is instance that memorizes initialization vector used for the {@link #cipher}
     */
    protected IvParameterSpec ivParameterSpec;

    /**
     * {@code secretKey} is instance that memorizes secret key used for the {@link #cipher}
     */
    protected SecretKey secretKey;

    /**
     * Constructor to init {@link AESClientCipher}
     *
     * @param ivParameterSpec: initialization vector as {@link String}
     * @param secretKey:       secret key used in the {@link Cipher} as {@link String}
     * @param algorithm:       algorithm used for AES cipher
     */
    public AESClientCipher(String ivParameterSpec, String secretKey, Algorithm algorithm) throws Exception {
        this(createIvParameter(ivParameterSpec), createSecretKey(secretKey), algorithm);
    }

    /**
     * Constructor to init {@link AESClientCipher}
     *
     * @param ivParameterSpec: initialization vector as {@link IvParameterSpec}
     * @param secretKey:       secret key used in the {@link Cipher} as {@link SecretKey}
     * @param algorithm:       algorithm used for AES cipher
     */
    public AESClientCipher(IvParameterSpec ivParameterSpec, SecretKey secretKey, Algorithm algorithm) throws Exception {
        super(algorithm);
        this.ivParameterSpec = ivParameterSpec;
        this.secretKey = secretKey;
        this.algorithm = algorithm;
    }

    /**
     * Method to encrypt any content
     *
     * @param content: content to encrypt
     * @return content encrypted as array of byte
     */
    @Override
    public <T> byte[] encrypt(T content) throws Exception {
        cipher.init(ENCRYPT_MODE, secretKey, ivParameterSpec);
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
        cipher.init(DECRYPT_MODE, secretKey, ivParameterSpec);
        return super.decrypt(content);
    }

    /**
     * This method is used to get {@link #ivParameterSpec} instance <br>
     * No-any params required
     *
     * @return {@link #ivParameterSpec} instance as {@link IvParameterSpec}
     */
    public IvParameterSpec getIvParameterSpec() {
        return ivParameterSpec;
    }

    /**
     * This method is used to get {@link #ivParameterSpec} instance as {@link String} <br>
     * No-any params required
     *
     * @return {@link #ivParameterSpec} instance as {@link String}
     */
    public String getBase64IvParameterSpec() {
        return encodeBase64(ivParameterSpec.getIV());
    }

    /**
     * This method is used to set the {@link #ivParameterSpec} instance
     *
     * @param ivParameterSpec: initialization vector used by {@link #cipher}
     */
    public void setIvParameterSpec(String ivParameterSpec) {
        this.ivParameterSpec = createIvParameter(ivParameterSpec);
    }

    /**
     * This method is used to set the {@link #ivParameterSpec} instance
     *
     * @param ivParameterSpec: initialization vector used by {@link #cipher}
     */
    public void setIvParameterSpec(IvParameterSpec ivParameterSpec) {
        this.ivParameterSpec = ivParameterSpec;
    }

    /**
     * This method is used to get {@link #secretKey} instance <br>
     * No-any params required
     *
     * @return {@link #secretKey} instance as {@link SecretKey}
     */
    public SecretKey getSecretKey() {
        return secretKey;
    }

    /**
     * This method is used to get {@link #secretKey} instance as {@link String} <br>
     * No-any params required
     *
     * @return {@link #secretKey} instance as {@link String}
     */
    public String getBase64SecretKey() {
        return encodeBase64(secretKey.getEncoded());
    }

    /**
     * This method is used to set the {@link #secretKey} instance
     *
     * @param secretKey: secret key used by {@link #cipher}
     */
    public void setSecretKey(String secretKey) {
        this.secretKey = createSecretKey(secretKey);
    }

    /**
     * This method is used to set the {@link #secretKey} instance
     *
     * @param secretKey: secret key used by {@link #cipher}
     */
    public void setSecretKey(SecretKey secretKey) {
        this.secretKey = secretKey;
    }

    /**
     * This method is used to create an initialization vector from a {@link String} object
     *
     * @param ivParameter: initialization vector as {@link String}
     * @return initialization vector as {@link IvParameterSpec}
     */
    public static IvParameterSpec createIvParameter(String ivParameter) {
        return new IvParameterSpec(decodeBase64(ivParameter));
    }

    /**
     * This method is used to create a secret key from a {@link String} object
     *
     * @param secretKey: secret key as {@link String}
     * @return secret key as {@link SecretKey}
     */
    public static SecretKey createSecretKey(String secretKey) {
        byte[] keyBytes = decodeBase64(secretKey);
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, AES_ALGORITHM_TYPE);
    }

}
