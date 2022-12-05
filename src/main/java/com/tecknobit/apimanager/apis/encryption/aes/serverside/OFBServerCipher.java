package com.tecknobit.apimanager.apis.encryption.aes.serverside;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.NoSuchAlgorithmException;

import static com.tecknobit.apimanager.apis.encryption.aes.ClientCipher.Algorithm.OFB_ALGORITHM;

/**
 * The {@code OFBServerCipher} class is useful for server side applications to cipher with AES-OFB mode
 *
 * @author Tecknobit N7ghtm4r3
 **/
public class OFBServerCipher extends GenericServerCipher {

    /**
     * Constructor to init {@link OFBServerCipher}
     *
     * @param ivParameterSpec: initialization vector as {@link IvParameterSpec}
     * @param secretKey:       secret key used in the {@link Cipher} as {@link SecretKey}
     * @param keySize:         size in bits for {@link #secretKey}
     **/
    public OFBServerCipher(IvParameterSpec ivParameterSpec, SecretKey secretKey,
                           KeySize keySize) throws NoSuchPaddingException, NoSuchAlgorithmException {
        super(ivParameterSpec, secretKey, OFB_ALGORITHM, keySize);
    }

    /**
     * Constructor to init {@link OFBServerCipher}
     *
     * @param ivParameterSpec: initialization vector as {@link String}
     * @param secretKey:       secret key used in the {@link Cipher} as {@link String}
     * @param keySize:         size in bits for {@link #secretKey}
     **/
    public OFBServerCipher(String ivParameterSpec, String secretKey,
                           KeySize keySize) throws NoSuchPaddingException, NoSuchAlgorithmException {
        super(ivParameterSpec, secretKey, OFB_ALGORITHM, keySize);
    }

    /**
     * This method is used to create an initialization vector <br>
     * Any params required
     *
     * @return initialization vector as {@link IvParameterSpec}
     **/
    public static IvParameterSpec createOFBIvParameterSpec() {
        return createIvParameterSpec();
    }

    /**
     * This method is used to create an initialization vector <br>
     * Any params required
     *
     * @return initialization vector as {@link String}
     **/
    public static String createOFBIvParameterSpecString() {
        return createIvParameterSpecString();
    }

    /**
     * This method is used to create a secret key
     *
     * @param keySize: size for secret key
     * @return secret key as {@link SecretKey}
     **/
    public static SecretKey createOFBSecretKey(KeySize keySize) throws NoSuchAlgorithmException {
        return createSecretKey(keySize);
    }

    /**
     * This method is used to create a secret key
     *
     * @param keySize: size for secret key
     * @return secret key as {@link String}
     **/
    public static String createOFBSecretKeyString(KeySize keySize) throws NoSuchAlgorithmException {
        return createSecretKeyString(keySize);
    }

}
