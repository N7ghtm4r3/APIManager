package com.tecknobit.apimanager.apis.encryption;

import com.tecknobit.apimanager.annotations.Structure;
import com.tecknobit.apimanager.apis.encryption.aes.AESClientCipher;
import org.json.JSONObject;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import java.security.NoSuchAlgorithmException;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Base64.getDecoder;
import static java.util.Base64.getEncoder;

@Structure
public abstract class BaseCipher {

    /**
     * {@code Algorithm} list of available algorithms for encryption
     */
    public enum Algorithm {

        /**
         * {@code "CBC_ALGORITHM"} algorithm
         */
        CBC_ALGORITHM("AES/CBC/PKCS5Padding"),

        /**
         * {@code "CFB_ALGORITHM"} algorithm
         */
        CFB_ALGORITHM("AES/CFB/NoPadding"),

        /**
         * {@code "OFB_ALGORITHM"} algorithm
         */
        OFB_ALGORITHM("AES/OFB/NoPadding"),

        /**
         * {@code "CTR_ALGORITHM"} algorithm
         */
        CTR_ALGORITHM("AES/CTR/NoPadding"),

        /**
         * {@code "RSA_ALGORITHM"} algorithm
         */
        RSA_ALGORITHM("RSA");

        /**
         * {@code "algorithm"} type
         */
        private final String algorithm;

        /**
         * Method to init {@link AESClientCipher.Algorithm}
         *
         * @param algorithm: algorithm type
         */
        Algorithm(String algorithm) {
            this.algorithm = algorithm;
        }

        /**
         * Method to get {@link #algorithm} instance <br>
         * Any params required
         *
         * @return {@link #algorithm} instance as {@link String}
         */
        @Override
        public String toString() {
            return algorithm;
        }

    }

    protected final Cipher cipher;

    protected Algorithm algorithm;

    public BaseCipher(Algorithm algorithm) throws NoSuchPaddingException, NoSuchAlgorithmException {
        this.algorithm = algorithm;
        cipher = Cipher.getInstance(algorithm.toString());
    }

    public BaseCipher(Cipher cipher) {
        algorithm = Algorithm.valueOf(cipher.getAlgorithm());
        this.cipher = cipher;
    }

    public <T> String encryptBase64(T content) throws Exception {
        return encodeBase64(encrypt(content));
    }

    public <T> byte[] encrypt(T content) throws Exception {
        return cipher.doFinal(content.toString().getBytes(UTF_8));
    }

    public String decryptBase64(String content) throws Exception {
        return decrypt(new String(decodeBase64(content)));
    }

    public <T> String decrypt(T content) throws Exception {
        return new String(cipher.doFinal(content.toString().getBytes(UTF_8)), UTF_8);
    }

    public Cipher getCipher() {
        return cipher;
    }

    public Algorithm getAlgorithm() {
        return algorithm;
    }

    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    public static String encodeBase64(byte[] source) {
        return getEncoder().encodeToString(source);
    }

    public static byte[] decodeBase64(String base64source) {
        return getDecoder().decode(base64source.getBytes());
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

}
