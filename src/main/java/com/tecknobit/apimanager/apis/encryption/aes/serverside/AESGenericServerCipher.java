package com.tecknobit.apimanager.apis.encryption.aes.serverside;

import com.tecknobit.apimanager.apis.encryption.aes.AESClientCipher;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * The {@code AESGenericServerCipher} class is useful for server side applications for general {@code "AES"}'s use
 *
 * @author Tecknobit N7ghtm4r3
 * @apiNote see the usage at <a href="https://github.com/N7ghtm4r3/APIManager/blob/main/documd/AES.md">AES.md</a>
 * @see AESClientCipher
 * @since 2.0.2
 */
public class AESGenericServerCipher extends AESClientCipher {

    /**
     * {@code AESKeySize} list of available key sizes
     */
    public enum AESKeySize {

        /**
         * {@code k128} key of 128 bits length
         */
        k128(128),

        /**
         * {@code k192} key of 192 bits length
         */
        k192(192),

        /**
         * {@code k256} key of 256 bits length
         */
        k256(256);

        /**
         * {@code size} of the key
         */
        private final int size;

        /**
         * Constructor to init {@link AESKeySize}
         *
         * @param size: size of the key
         */
        AESKeySize(int size) {
            this.size = size;
        }

        /**
         * This method is used to get {@link #size} instance <br>
         * Any params required
         *
         * @return {@link #size} instance as int
         */
        public int getSize() {
            return size;
        }

        /**
         * This method is used to get {@link #size} instance <br>
         * Any params required
         *
         * @return {@link #size} instance as {@link String}
         */
        @Override
        public String toString() {
            return String.valueOf(size);
        }

    }

    /**
     * {@code keySize} size of the key used to cipher and to decipher the content
     */
    protected final AESKeySize keySize;

    /**
     * Constructor to init {@link AESGenericServerCipher}
     *
     * @param ivParameterSpec: initialization vector as {@link IvParameterSpec}
     * @param secretKey:       secret key used in the {@link Cipher} as {@link SecretKey}
     * @param algorithm:       algorithm used for AES cipher
     * @param keySize:         size of the key used to cipher and to decipher the content
     */
    public AESGenericServerCipher(IvParameterSpec ivParameterSpec, SecretKey secretKey, Algorithm algorithm,
                                  AESKeySize keySize) throws NoSuchPaddingException, NoSuchAlgorithmException {
        super(ivParameterSpec, secretKey, algorithm);
        this.keySize = keySize;
    }

    /**
     * Constructor to init {@link AESGenericServerCipher}
     *
     * @param ivParameterSpec: initialization vector as {@link IvParameterSpec}
     * @param secretKey:       secret key used in the {@link Cipher} as {@link SecretKey}
     * @param algorithm:       algorithm used for AES cipher
     * @param keySize:         size of the key used to cipher and to decipher the content
     */
    public AESGenericServerCipher(String ivParameterSpec, String secretKey, Algorithm algorithm,
                                  AESKeySize keySize) throws NoSuchPaddingException, NoSuchAlgorithmException {
        super(ivParameterSpec, secretKey, algorithm);
        this.keySize = keySize;
    }

    /**
     * This method is used to get {@link #keySize} instance <br>
     * Any params required
     *
     * @return {@link #keySize} instance as {@link AESKeySize}
     */
    public AESKeySize getKeySize() {
        return keySize;
    }

    /**
     * This method is used to create an initialization vector <br>
     * Any params required
     *
     * @return initialization vector as {@link String}
     */
    public static String createIvParameterSpecString() {
        return byteToString(createIvParameterSpec().getIV());
    }

    /**
     * This method is used to create an initialization vector <br>
     * Any params required
     *
     * @return initialization vector as {@link IvParameterSpec}
     */
    public static IvParameterSpec createIvParameterSpec() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    /**
     * This method is used to create a secret key
     *
     * @param keySize: size for secret key
     * @return secret key as {@link SecretKey}
     */
    public static SecretKey createSecretKey(AESKeySize keySize) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES_ALGORITHM_TYPE);
        keyGenerator.init(keySize.getSize());
        return keyGenerator.generateKey();
    }

    /**
     * This method is used to create a secret key
     *
     * @param keySize: size for secret key
     * @return secret key as {@link String}
     */
    public static String createSecretKeyString(AESKeySize keySize) throws NoSuchAlgorithmException {
        return byteToString(createSecretKey(keySize).getEncoded());
    }

}
