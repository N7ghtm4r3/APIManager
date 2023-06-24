package com.tecknobit.apimanager.apis.encryption.aes;

import com.tecknobit.apimanager.apis.encryption.BaseCipher;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Base64;

/**
 * The {@code AESServerCipher} class is useful for server side applications for {@code "AES"}'s encryption or decryption
 *
 * @author Tecknobit N7ghtm4r3
 * @apiNote see the usage at <a href="https://github.com/N7ghtm4r3/APIManager/blob/main/documd/AES.md">AES.md</a>
 * @see BaseCipher
 * @see AESClientCipher
 * @since 2.0.2
 */
public class AESServerCipher extends AESClientCipher {

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
         * No-any params required
         *
         * @return {@link #size} instance as int
         */
        public int getSize() {
            return size;
        }

        /**
         * This method is used to get {@link #size} instance <br>
         * No-any params required
         *
         * @return {@link #size} instance as {@link String}
         */
        @Override
        public String toString() {
            return String.valueOf(size);
        }

    }

    /**
     * Constructor to init {@link AESServerCipher}
     *
     * @param ivParameterSpec : initialization vector as {@link IvParameterSpec}
     * @param secretKey       :       secret key used in the {@link Cipher} as {@link SecretKey}
     * @param algorithm       :       algorithm used for AES cipher
     */
    public AESServerCipher(IvParameterSpec ivParameterSpec, SecretKey secretKey, Algorithm algorithm) throws Exception {
        super(ivParameterSpec, secretKey, algorithm);
    }

    /**
     * Constructor to init {@link AESServerCipher}
     *
     * @param ivParameterSpec : initialization vector as {@link IvParameterSpec}
     * @param secretKey       :       secret key used in the {@link Cipher} as {@link SecretKey}
     * @param algorithm       :       algorithm used for AES cipher
     */
    public AESServerCipher(String ivParameterSpec, String secretKey, Algorithm algorithm) throws Exception {
        super(ivParameterSpec, secretKey, algorithm);
    }

    /**
     * This method is used to create an initialization vector <br>
     * No-any params required
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
     * This method is used to create an initialization vector <br>
     * No-any params required
     *
     * @return initialization vector as {@link Base64}-{@link String}
     */
    public static String createBase64IvParameterSpec() {
        return encodeBase64(createIvParameterSpec().getIV());
    }

    /**
     * This method is used to create a secret key
     *
     * @param keySize: size for secret key
     * @return secret key as {@link Base64}-{@link String}
     */
    public static String createBase64SecretKey(AESKeySize keySize) throws NoSuchAlgorithmException {
        return encodeBase64(createSecretKey(keySize).getEncoded());
    }

}
