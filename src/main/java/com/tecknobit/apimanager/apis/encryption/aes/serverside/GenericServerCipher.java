package com.tecknobit.apimanager.apis.encryption.aes.serverside;

import com.tecknobit.apimanager.apis.encryption.aes.ClientCipher;
import com.tecknobit.apimanager.apis.encryption.exceptions.*;

import javax.crypto.*;
import javax.crypto.spec.IvParameterSpec;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;

/**
 * The {@code GenericServerCipher} class is useful for server side applications for general {@code "AES"}'s use
 *
 * @author Tecknobit N7ghtm4r3
 **/
public class GenericServerCipher extends ClientCipher {

    /**
     * {@code MINIMUM_KEY_SIZE} is constant that memorizes minimum key size to create a {@link SecretKey}
     **/
    public static final int MINIMUM_KEY_SIZE = 128;
    /**
     * {@code MEDIUM_KEY_SIZE} is constant that memorizes medium key size to create a {@link SecretKey}
     **/
    public static final int MEDIUM_KEY_SIZE = 192;
    /**
     * {@code MAXIMUM_KEY_SIZE} is constant that memorizes maximum key size to create a {@link SecretKey}
     **/
    public static final int MAXIMUM_KEY_SIZE = 256;
    /**
     * {@code keySize} is instance that memorizes key size used in this communication session
     **/
    protected final int keySize;

    /**
     * Constructor to init {@link GenericServerCipher}
     *
     * @param ivParameterSpec: initialization vector as {@link IvParameterSpec}
     * @param secretKey:       secret key used in the {@link Cipher} as {@link SecretKey}
     * @param algorithm:       algorithm used for AES cipher
     * @param keySize:         size in bits for {@link #secretKey}
     **/
    public GenericServerCipher(IvParameterSpec ivParameterSpec, SecretKey secretKey, Algorithm algorithm, int keySize) throws
            NoSuchPaddingException, NoSuchAlgorithmException, KeySizeException {
        super(ivParameterSpec, secretKey, algorithm);
        if (keySize != MINIMUM_KEY_SIZE && keySize != MEDIUM_KEY_SIZE && keySize != MAXIMUM_KEY_SIZE)
            throw new KeySizeException();
        else
            this.keySize = keySize;
    }

    /**
     * Constructor to init {@link GenericServerCipher}
     *
     * @param ivParameterSpec: initialization vector as {@link IvParameterSpec}
     * @param secretKey:       secret key used in the {@link Cipher} as {@link SecretKey}
     * @param algorithm:       algorithm used for AES cipher
     * @param keySize:         size in bits for {@link #secretKey}
     **/
    public GenericServerCipher(String ivParameterSpec, String secretKey, Algorithm algorithm, int keySize) throws
            NoSuchPaddingException, NoSuchAlgorithmException, KeySizeException {
        super(ivParameterSpec, secretKey, algorithm);
        if (keySize != MINIMUM_KEY_SIZE && keySize != MEDIUM_KEY_SIZE && keySize != MAXIMUM_KEY_SIZE)
            throw new KeySizeException();
        else
            this.keySize = keySize;
    }

    /**
     * This method is used to create an initialization vector <br>
     * Any params required
     *
     * @return initialization vector as {@link IvParameterSpec}
     **/
    public static IvParameterSpec createIvParameterSpec() {
        byte[] iv = new byte[16];
        new SecureRandom().nextBytes(iv);
        return new IvParameterSpec(iv);
    }

    /**
     * This method is used to create an initialization vector <br>
     * Any params required
     *
     * @return initialization vector as {@link String}
     **/
    public static String createIvParameterSpecString() {
        return byteToString(createIvParameterSpec().getIV());
    }

    /**
     * This method is used to create a secret key
     *
     * @param keySize: size for secret key
     * @return secret key as {@link SecretKey}
     * @implNote sizes admitted are {@link #MINIMUM_KEY_SIZE}, {@link #MEDIUM_KEY_SIZE} or {@link #MAXIMUM_KEY_SIZE}
     **/
    public static SecretKey createSecretKey(int keySize) throws NoSuchAlgorithmException, KeySizeException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES_ALGORITHM_TYPE);
        if (keySize == MINIMUM_KEY_SIZE || keySize == MEDIUM_KEY_SIZE || keySize == MAXIMUM_KEY_SIZE) {
            keyGenerator.init(keySize);
            return keyGenerator.generateKey();
        } else
            throw new KeySizeException();
    }

    /**
     * This method is used to create a secret key
     *
     * @param keySize: size for secret key
     * @return secret key as {@link String}
     * @implNote sizes admitted are {@link #MINIMUM_KEY_SIZE}, {@link #MEDIUM_KEY_SIZE} or {@link #MAXIMUM_KEY_SIZE}
     **/
    public static String createSecretKeyString(int keySize) throws NoSuchAlgorithmException, KeySizeException {
        return byteToString(createSecretKey(keySize).getEncoded());
    }

    /**
     * This method is used to decryptResponse a request
     *
     * @param request: request to decryptResponse
     * @return plain request decrypted as {@link String} es. your plain text
     **/
    public String decryptRequest(String request) throws InvalidAlgorithmParameterException, IllegalBlockSizeException,
            BadPaddingException, InvalidKeyException {
        return decryptResponse(request);
    }

    /**
     * This method is used to encryptRequest a response
     *
     * @param response: response to cipher
     * @return plain response ciphered as {@link String} es. 26XBx/esnnrehi/GH3tpnQ==
     **/
    public String encryptResponse(String response) throws InvalidAlgorithmParameterException, IllegalBlockSizeException,
            BadPaddingException, InvalidKeyException {
        return encryptRequest(response);
    }

    /**
     * This method is used to get {@link #keySize} instance <br>
     * Any params required
     *
     * @return {@link #keySize} instance as int
     **/
    public int getKeySize() {
        return keySize;
    }

    public enum KeySize {

        
    }

}
