package com.tecknobit.apimanager.apis.encryption;

import com.tecknobit.apimanager.annotations.Structure;
import com.tecknobit.apimanager.annotations.Wrapper;
import com.tecknobit.apimanager.apis.encryption.aes.AESClientCipher;
import org.json.JSONObject;

import javax.crypto.Cipher;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;
import static java.util.Base64.getDecoder;
import static java.util.Base64.getEncoder;

/**
 * The {@code BaseCipher} class is useful to manage the different encryption algorithms
 *
 * @author Tecknobit N7ghtm4r3
 * @see BaseCipher
 * @since 2.1.5
 */
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
         * No-any params required
         *
         * @return {@link #algorithm} instance as {@link String}
         */
        @Override
        public String toString() {
            return algorithm;
        }

    }

    /**
     * {@code cipher} to manage the encryption and the decryption phases
     */
    protected final Cipher cipher;

    /**
     * {@code algorithm} used during the encryption and the decryption phases
     */
    protected Algorithm algorithm;

    /**
     * Constructor to init a {@link BaseCipher} object
     *
     * @param algorithm: the algorithm used during the encryption and the decryption phases
     */
    public BaseCipher(Algorithm algorithm) throws Exception {
        this.algorithm = algorithm;
        cipher = Cipher.getInstance(algorithm.toString());
    }

    /**
     * Constructor to init a {@link BaseCipher} object
     *
     * @param cipher: the cipher to manage the encryption and the decryption phases
     */
    public BaseCipher(Cipher cipher) {
        algorithm = Algorithm.valueOf(cipher.getAlgorithm());
        this.cipher = cipher;
    }

    /**
     * Method to encrypt and format in {@link Base64} any content
     *
     * @param content: content to encrypt
     * @return content encrypted as {@link Base64}-{@link String}
     */
    @Wrapper
    public <T> String encryptBase64(T content) throws Exception {
        return encodeBase64(encrypt(content));
    }

    /**
     * Method to encrypt any content
     *
     * @param content: content to encrypt
     * @return content encrypted as array of byte
     */
    public <T> byte[] encrypt(T content) throws Exception {
        return cipher.doFinal(content.toString().getBytes(UTF_8));
    }

    /**
     * Method to decrypt a {@link Base64} content
     *
     * @param content: content to decrypt
     * @return {@link Base64} content decrypted as {@link String}
     */
    @Wrapper
    public String decryptBase64(String content) throws Exception {
        return new String(decrypt(decodeBase64(content)), UTF_8);
    }

    /**
     * Method to decrypt a content
     *
     * @param content: content to decrypt
     * @return content decrypted as array of byte
     */
    public byte[] decrypt(byte[] content) throws Exception {
        return cipher.doFinal(content);
    }

    /**
     * Method to get {@link #cipher} instance <br>
     * No-any params required
     *
     * @return {@link #cipher} instance as {@link Cipher}
     */
    public Cipher getCipher() {
        return cipher;
    }

    /**
     * Method to get {@link #algorithm} instance <br>
     * No-any params required
     *
     * @return {@link #algorithm} instance as {@link Algorithm}
     */
    public Algorithm getAlgorithm() {
        return algorithm;
    }

    /**
     * Method to set {@link #algorithm} instance
     *
     * @param algorithm: used during the encryption and the decryption phases
     */
    public void setAlgorithm(Algorithm algorithm) {
        this.algorithm = algorithm;
    }

    /**
     * Method to encode in {@link Base64} a source
     *
     * @param source: source to encode
     * @return source in {@link Base64} encoded as {@link String}
     */
    @Wrapper
    public static String encodeBase64(String source) {
        return encodeBase64(source.getBytes());
    }

    /**
     * Method to encode in {@link Base64} a source
     *
     * @param source: source to encode
     * @return source in {@link Base64} encoded as {@link String}
     */
    public static String encodeBase64(byte[] source) {
        return getEncoder().encodeToString(source);
    }

    /**
     * Method to decode a {@link Base64} source
     *
     * @param source: source to decode
     * @return {@link Base64} source decoded as array of byte
     */
    public static byte[] decodeBase64(String source) {
        return getDecoder().decode(source.getBytes());
    }

    /**
     * Returns a string representation of the object <br>
     * No-any params required
     *
     * @return a string representation of the object as {@link String}
     */
    @Override
    public String toString() {
        return new JSONObject(this).toString();
    }

}
