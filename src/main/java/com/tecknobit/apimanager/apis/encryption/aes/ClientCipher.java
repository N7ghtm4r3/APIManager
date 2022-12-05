package com.tecknobit.apimanager.apis.encryption.aes;

import org.json.JSONObject;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

import static java.util.Base64.getDecoder;
import static java.util.Base64.getEncoder;
import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;

/**
 * The {@code ClientCipher} class is useful for general {@code "AES"}'s use
 *
 * @author Tecknobit N7ghtm4r3
 **/
public class ClientCipher {

    /**
     * {@code AES_ALGORITHM_TYPE} is constant that memorizes algorithm type used for the {@link #cipher}
     **/
    public static final String AES_ALGORITHM_TYPE = "AES";
    /**
     * {@code ivParameterSpec} is instance that memorizes initialization vector used for the {@link #cipher}
     **/
    protected final IvParameterSpec ivParameterSpec;
    /**
     * {@code secretKey} is instance that memorizes secret key used for the {@link #cipher}
     **/
    protected final SecretKey secretKey;
    /**
     * {@code cipher} is instance that memorizes {@link Cipher} object
     **/
    protected final Cipher cipher;
    /**
     * {@code algorithm} is instance that memorizes algorithm for {@link #cipher}
     **/
    protected final Algorithm algorithm;

    /**
     * Constructor to init {@link ClientCipher}
     *
     * @param ivParameterSpec: initialization vector as {@link String}
     * @param secretKey:       secret key used in the {@link Cipher} as {@link String}
     * @param algorithm:       algorithm used for AES cipher
     **/
    public ClientCipher(String ivParameterSpec, String secretKey, Algorithm algorithm) throws NoSuchAlgorithmException,
            NoSuchPaddingException {
        this(createIvParameter(ivParameterSpec), createSecretKey(secretKey), algorithm);
    }

    /**
     * Constructor to init {@link ClientCipher}
     *
     * @param ivParameterSpec: initialization vector as {@link IvParameterSpec}
     * @param secretKey:       secret key used in the {@link Cipher} as {@link SecretKey}
     * @param algorithm:       algorithm used for AES cipher
     **/
    public ClientCipher(IvParameterSpec ivParameterSpec, SecretKey secretKey, Algorithm algorithm) throws NoSuchAlgorithmException,
            NoSuchPaddingException {
        if (ivParameterSpec == null)
            throw new IllegalArgumentException("Initialization vector cannot be null");
        else
            this.ivParameterSpec = ivParameterSpec;
        if (secretKey == null)
            throw new IllegalArgumentException("Secret key cannot be null");
        else
            this.secretKey = secretKey;
        this.algorithm = algorithm;
        cipher = Cipher.getInstance(algorithm.toString());
    }

    /**
     * This method is used to create an initialization vector from a {@link String} object
     *
     * @param ivParameter: initialization vector as {@link String}
     * @return initialization vector as {@link IvParameterSpec}
     **/
    public static IvParameterSpec createIvParameter(String ivParameter) {
        return new IvParameterSpec(stringToBytes(ivParameter));
    }

    /**
     * This method is used to create a secret key from a {@link String} object
     *
     * @param secretKey: secret key as {@link String}
     * @return secret key as {@link SecretKey}
     **/
    public static SecretKey createSecretKey(String secretKey) {
        byte[] keyBytes = stringToBytes(secretKey);
        return new SecretKeySpec(keyBytes, 0, keyBytes.length, AES_ALGORITHM_TYPE);
    }

    /**
     * This method is used to obtain a byte array from a {@link Base64} {@link String}
     *
     * @param sourceString: source to get byte
     * @return byte array
     **/
    public static byte[] stringToBytes(String sourceString) {
        return getDecoder().decode(sourceString);
    }

    /**
     * This method is used to obtain a {@link String} from a {@link Base64} byte array
     *
     * @param bytes: source to get string
     * @return string as {@link String}
     **/
    public static String byteToString(byte[] bytes) {
        return getEncoder().encodeToString(bytes);
    }

    /**
     * This method is used to encrypt a request
     *
     * @param request: request to cipher
     * @return plain request ciphered as {@link String} es. 26XBx/esnnrehi/GH3tpnQ==
     **/
    public String encryptRequest(String request) throws IllegalBlockSizeException, BadPaddingException,
            InvalidAlgorithmParameterException, InvalidKeyException {
        cipher.init(ENCRYPT_MODE, secretKey, ivParameterSpec);
        return getEncoder().encodeToString(cipher.doFinal(request.getBytes()));
    }

    /**
     * This method is used to decrypt a response
     *
     * @param response: response to decrypt
     * @return plain response decrypted as {@link String} es. your plain text
     **/
    public String decryptResponse(String response) throws IllegalBlockSizeException, BadPaddingException,
            InvalidAlgorithmParameterException, InvalidKeyException {
        cipher.init(DECRYPT_MODE, secretKey, ivParameterSpec);
        return new String(cipher.doFinal(getDecoder().decode(response)));
    }

    /**
     * This method is used to get {@link #cipher} instance <br>
     * Any params required
     *
     * @return {@link #cipher} instance as {@link Cipher}
     **/
    public Cipher getCipher() {
        return cipher;
    }

    /**
     * This method is used to get {@link #algorithm} instance <br>
     * Any params required
     *
     * @return {@link #algorithm} instance as {@link Algorithm}
     **/
    public Algorithm getAlgorithm() {
        return algorithm;
    }

    /**
     * This method is used to get {@link #ivParameterSpec} instance <br>
     * Any params required
     *
     * @return {@link #ivParameterSpec} instance as {@link IvParameterSpec}
     **/
    public IvParameterSpec getIvParameterSpec() {
        return ivParameterSpec;
    }

    /**
     * This method is used to get {@link #ivParameterSpec} instance as {@link String} <br>
     * Any params required
     *
     * @return {@link #ivParameterSpec} instance as {@link String}
     **/
    public String getStringIvParameterSpec() {
        return byteToString(ivParameterSpec.getIV());
    }

    /**
     * This method is used to get {@link #secretKey} instance <br>
     * Any params required
     *
     * @return {@link #secretKey} instance as {@link SecretKey}
     **/
    public SecretKey getSecretKey() {
        return secretKey;
    }

    /**
     * This method is used to get {@link #secretKey} instance as {@link String} <br>
     * Any params required
     *
     * @return {@link #secretKey} instance as {@link String}
     **/
    public String getStringSecretKey() {
        return byteToString(secretKey.getEncoded());
    }

    /**
     * Returns a string representation of the object <br>
     * Any params required
     *
     * @return a string representation of the object as {@link String}
     */
    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }

    /**
     * {@code Algorithm} list of available algorithms for {@code "AES"}'s use
     **/
    public enum Algorithm {

        /**
         * {@code "CBC_ALGORITHM"} algorithm
         **/
        CBC_ALGORITHM("AES/CBC/PKCS5Padding"),

        /**
         * {@code "CFB_ALGORITHM"} algorithm
         **/
        CFB_ALGORITHM("AES/CFB/NoPadding"),

        /**
         * {@code "OFB_ALGORITHM"} algorithm
         **/
        OFB_ALGORITHM("AES/OFB/NoPadding"),

        /**
         * {@code "CTR_ALGORITHM"} algorithm
         **/
        CTR_ALGORITHM("AES/CTR/NoPadding");

        /**
         * {@code "algorithm"} type
         **/
        private final String algorithm;

        /**
         * Method to init {@link Algorithm}
         *
         * @param algorithm: algorithm type
         **/
        Algorithm(String algorithm) {
            this.algorithm = algorithm;
        }

        /**
         * Method to get {@link #algorithm} instance <br>
         * Any params required
         *
         * @return {@link #algorithm} instance as {@link String}
         **/
        @Override
        public String toString() {
            return algorithm;
        }

    }

}
