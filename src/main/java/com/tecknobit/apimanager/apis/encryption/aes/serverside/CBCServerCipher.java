package com.tecknobit.apimanager.apis.encryption.aes.serverside;

import com.tecknobit.apimanager.apis.encryption.aes.ClientCipher;

import javax.crypto.Cipher;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import java.security.NoSuchAlgorithmException;

import static com.tecknobit.apimanager.apis.encryption.aes.ClientCipher.Algorithm.CBC_ALGORITHM;

/**
 * The {@code CBCServerCipher} class is useful for server side applications to cipher with AES-CBC mode
 *
 * @author Tecknobit N7ghtm4r3
 * @apiNote see the usage at <a href="https://github.com/N7ghtm4r3/APIManager/blob/main/documd/AES.md">AES.md</a>
 * @see ClientCipher
 * @see GenericServerCipher
 * @since 2.0.2
 **/
public class CBCServerCipher extends GenericServerCipher {

    /**
     * Constructor to init {@link CBCServerCipher}
     *
     * @param ivParameterSpec: initialization vector as {@link IvParameterSpec}
     * @param secretKey:       secret key used in the {@link Cipher} as {@link SecretKey}
     * @param keySize:         size in bits for {@link #secretKey}
     **/
    public CBCServerCipher(IvParameterSpec ivParameterSpec, SecretKey secretKey,
                           KeySize keySize) throws NoSuchPaddingException, NoSuchAlgorithmException {
        super(ivParameterSpec, secretKey, CBC_ALGORITHM, keySize);
    }

    /**
     * Constructor to init {@link CBCServerCipher}
     *
     * @param ivParameterSpec: initialization vector as {@link String}
     * @param secretKey:       secret key used in the {@link Cipher} as {@link String}
     * @param keySize:         size in bits for {@link #secretKey}
     **/
    public CBCServerCipher(String ivParameterSpec, String secretKey,
                           KeySize keySize) throws NoSuchPaddingException, NoSuchAlgorithmException {
        super(ivParameterSpec, secretKey, CBC_ALGORITHM, keySize);
    }

    /**
     * This method is used to create an initialization vector <br>
     * Any params required
     *
     * @return initialization vector as {@link IvParameterSpec}
     **/
    public static IvParameterSpec createCBCIvParameterSpec() {
        return createIvParameterSpec();
    }

    /**
     * This method is used to create an initialization vector <br>
     * Any params required
     *
     * @return initialization vector as {@link String}
     **/
    public static String createCBCIvParameterSpecString() {
        return createIvParameterSpecString();
    }

    /**
     * This method is used to create a secret key
     *
     * @param keySize: size for secret key
     * @return secret key as {@link SecretKey}
     **/
    public static SecretKey createCBCSecretKey(KeySize keySize) throws NoSuchAlgorithmException {
        return createSecretKey(keySize);
    }

    /**
     * This method is used to create a secret key
     *
     * @param keySize: size for secret key
     * @return secret key as {@link String}
     **/
    public static String createCBCSecretKeyString(KeySize keySize) throws NoSuchAlgorithmException {
        return createSecretKeyString(keySize);
    }

}
