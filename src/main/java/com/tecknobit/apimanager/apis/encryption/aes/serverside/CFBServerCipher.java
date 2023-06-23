package com.tecknobit.apimanager.apis.encryption.aes.serverside;

import com.tecknobit.apimanager.apis.encryption.aes.AESClientCipher;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.security.NoSuchAlgorithmException;

import static com.tecknobit.apimanager.apis.encryption.aes.AESClientCipher.Algorithm.CFB_ALGORITHM;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.toHexString;

/**
 * The {@code CFBServerCipher} class is useful for server side applications to cipher with AES-CFB mode
 *
 * @author Tecknobit N7ghtm4r3
 * @apiNote see the usage at <a href="https://github.com/N7ghtm4r3/APIManager/blob/main/documd/AES.md">AES.md</a>
 * @see AESClientCipher
 * @see AESGenericServerCipher
 * @since 2.0.2
 */
public class CFBServerCipher extends AESGenericServerCipher {

    /**
     * {@code IV_SOURCE_LENGTH} is constant that memorizes standard size for {@link IvParameterSpec}
     */
    public static final int IV_SOURCE_LENGTH = 32;

    /**
     * {@code KEY_SOURCE_LENGTH} is constant that memorizes standard size for {@link SecretKeySpec}
     */
    public static final int KEY_SOURCE_LENGTH = 64;

    /**
     * Constructor to init {@link CFBServerCipher}
     *
     * @param ivParameterSpec: initialization vector as {@link IvParameterSpec}
     * @param secretKey:       secret key used in the {@link Cipher} as {@link SecretKey}
     */
    public CFBServerCipher(IvParameterSpec ivParameterSpec, SecretKey secretKey) throws NoSuchPaddingException,
            NoSuchAlgorithmException {
        super(ivParameterSpec, secretKey, CFB_ALGORITHM, AESKeySize.k256);
    }

    /**
     * Constructor to init {@link CBCServerCipher}
     *
     * @param ivParameterSpec: initialization vector as {@link String}
     * @param secretKey:       secret key used in the {@link Cipher} as {@link String}
     */
    public CFBServerCipher(String ivParameterSpec, String secretKey) throws NoSuchPaddingException, NoSuchAlgorithmException {
        super(ivParameterSpec, secretKey, CFB_ALGORITHM, AESKeySize.k256);
    }

    /**
     * This method is used to create an initialization vector
     *
     * @param source: source string to create an initialization vector
     * @return initialization vector as {@link IvParameterSpec}
     */
    public static IvParameterSpec createCFBIvParameterSpec(String source) {
        checkKeyValidity(source, IV_SOURCE_LENGTH);
        return new IvParameterSpec(hexToBytes(source));
    }

    /**
     * This method is used to create an initialization vector
     *
     * @param source: source string to create an initialization vector
     * @return initialization vector as {@link String}
     */
    public static String createCFBIvParameterSpecString(String source) {
        return byteToString(createCFBIvParameterSpec(source).getIV());
    }

    /**
     * This method is used to create a secret key
     *
     * @param source: source string to create a secret key
     * @return secret key as {@link SecretKey}
     */
    public static SecretKeySpec createCFBSecretKey(String source) {
        checkKeyValidity(source, KEY_SOURCE_LENGTH);
        return new SecretKeySpec(hexToBytes(source), AES_ALGORITHM_TYPE);
    }

    /**
     * This method is used to create a secret key
     *
     * @param source: source string to create a secret key
     * @return secret key as {@link String}
     */
    public static String createCFBSecretKeyString(String source) {
        return byteToString(createCFBSecretKey(source).getEncoded());
    }

    /**
     * This method is used to create an initialization vector <br>
     * Any params required
     *
     * @return initialization vector as {@link IvParameterSpec}
     */
    public static IvParameterSpec createCFBIvParameterSpec() {
        return createIvParameterSpec();
    }

    /**
     * This method is used to create an initialization vector <br>
     * Any params required
     *
     * @return initialization vector as {@link String}
     */
    public static String createCFBIvParameterSpecString() {
        return createIvParameterSpecString();
    }

    /**
     * This method is used to create a secret key
     *
     * @param keySize: size for secret key
     * @return secret key as {@link SecretKey}
     */
    public static SecretKey createCFBSecretKey(AESKeySize keySize) throws NoSuchAlgorithmException {
        return createSecretKey(keySize);
    }

    /**
     * This method is used to create a secret key
     *
     * @param keySize: size for secret key
     * @return secret key as {@link String}
     */
    public static String createCFBSecretKeyString(AESKeySize keySize) throws NoSuchAlgorithmException {
        return createSecretKeyString(keySize);
    }

    /**
     * This method is used to check validity of a key
     *
     * @param source:    source string to check validity
     * @param keyLength: length used for check -> {@link #IV_SOURCE_LENGTH} or {@link #KEY_SOURCE_LENGTH}
     */
    private static void checkKeyValidity(String source, int keyLength) {
        int sourceLength = keyLength;
        String keyType = "an initialization vector";
        if (keyLength == KEY_SOURCE_LENGTH)
            keyType = "a secret key";
        if (source != null)
            sourceLength = source.length();
        if (sourceLength < keyLength || sourceLength > keyLength) {
            throw new IllegalArgumentException("Source string to create " + keyType + " must have a " +
                    keyLength + " characters, actual length: " + sourceLength);
        }
        assert source != null;
        source = source.toUpperCase();
        for (int j = 0; j < sourceLength; j++)
            if ((int) source.charAt(j) > (int) 'F')
                throw new IllegalArgumentException("The source string can contain the character up to f and F");
    }

    /**
     * This method is used to convert a {@link String} in byte array
     *
     * @param source: source string to convert
     * @return hex string in byte array format
     */
    public static byte[] hexToBytes(String source) {
        if (source == null)
            throw new IllegalArgumentException("Source string cannot be null");
        int length = source.length();
        if (length % 2 == 1)
            throw new IllegalArgumentException("Source string must have even length");
        byte[] bytes = new byte[length / 2];
        for (int i = 0; i != length / 2; i++) {
            int j = i * 2;
            bytes[i] = (byte) parseInt(source.substring(j, j + 2), 16);
        }
        return bytes;
    }

    /**
     * This method is used to convert byte array in hexadecimal {@link String}
     *
     * @param bytes: source byte array to convert
     * @return byte array in hexadecimal {@link String}
     */
    public static String bytesToHex(byte[] bytes) {
        StringBuilder result = new StringBuilder();
        for (byte mByte : bytes) {
            String hexString = toHexString(mByte & 255);
            if (hexString.length() == 1)
                result.append("0").append(hexString);
            else
                result.append(result).append(hexString);
        }
        return result.toString().toUpperCase();
    }

    /**
     * This method is used to get key size <br>
     * Any params required
     *
     * @return standard size of key for CFB mode is 512 bits, so will be returned <b>null</b>
     */
    public AESKeySize getKeySize() {
        return null;
    }

    /**
     * This method is used to get key size<br>
     * <p>
     * Any params required
     *
     * @return key size instance as int
     * @implNote standard size of key for CFB mode is 512 bits
     */
    public int getCFBKeySize() {
        return 512;
    }

}
