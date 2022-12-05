package com.tecknobit.apimanager.apis.encryption.aes.serverside;

import com.tecknobit.apimanager.apis.encryption.exceptions.*;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.NoSuchAlgorithmException;

import static com.tecknobit.apimanager.apis.encryption.aes.ClientCipher.Algorithm.CTR_ALGORITHM;

/**
 * The {@code CTRServerCipher} class is useful for server side applications to cipher with AES-CTR mode
 *
 * @author Tecknobit N7ghtm4r3
 **/
public class CTRServerCipher extends GenericServerCipher {

    /**
     * Constructor to init {@link CTRServerCipher}
     *
     * @param ivParameterSpec: initialization vector as {@link IvParameterSpec}
     * @param secretKey:       secret key used in the {@link Cipher} as {@link SecretKey}
     * @param keySize:         size in bits for {@link #secretKey}
     **/
    public CTRServerCipher(IvParameterSpec ivParameterSpec, SecretKey secretKey, int keySize) throws
            NoSuchPaddingException, NoSuchAlgorithmException, KeySizeException {
        super(ivParameterSpec, secretKey, CTR_ALGORITHM, keySize);
    }

    /**
     * Constructor to init {@link CBCServerCipher}
     *
     * @param ivParameterSpec: initialization vector as {@link String}
     * @param secretKey:       secret key used in the {@link Cipher} as {@link String}
     * @param keySize:         size in bits for {@link #secretKey}
     **/
    public CTRServerCipher(String ivParameterSpec, String secretKey, int keySize) throws
            NoSuchPaddingException, NoSuchAlgorithmException, KeySizeException {
        super(ivParameterSpec, secretKey, CTR_ALGORITHM, keySize);
    }

    /**
     * This method is used to create an initialization vector <br>
     * Any params required
     *
     * @return initialization vector as {@link IvParameterSpec}
     **/
    public static IvParameterSpec createCTRIvParameterSpec() {
        return createIvParameterSpec();
    }

    /**
     * This method is used to create an initialization vector <br>
     * Any params required
     *
     * @return initialization vector as {@link String}
     **/
    public static String createCTRIvParameterSpecString() {
        return createIvParameterSpecString();
    }

    /**
     * This method is used to create a secret key
     *
     * @param keySize: size for secret key
     * @return secret key as {@link SecretKey}
     * @implNote sizes admitted are {@link #MINIMUM_KEY_SIZE}, {@link #MEDIUM_KEY_SIZE} or {@link #MAXIMUM_KEY_SIZE}
     **/
    public static SecretKey createCTRSecretKey(int keySize) throws KeySizeException, NoSuchAlgorithmException {
        return createSecretKey(keySize);
    }

    /**
     * This method is used to create a secret key
     *
     * @param keySize: size for secret key
     * @return secret key as {@link String}
     * @implNote sizes admitted are {@link #MINIMUM_KEY_SIZE}, {@link #MEDIUM_KEY_SIZE} or {@link #MAXIMUM_KEY_SIZE}
     **/
    public static String createCTRSecretKeyString(int keySize) throws KeySizeException, NoSuchAlgorithmException {
        return createSecretKeyString(keySize);
    }

}