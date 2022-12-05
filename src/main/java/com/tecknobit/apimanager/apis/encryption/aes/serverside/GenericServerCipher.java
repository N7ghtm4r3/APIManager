package com.tecknobit.apimanager.apis.encryption.aes.serverside;

import com.tecknobit.apimanager.apis.encryption.aes.ClientCipher;

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
 * @apiNote see the usage at <a href="https://github.com/N7ghtm4r3/APIManager/blob/main/documd/AES.md">AES.md</a>
 * @see ClientCipher
 **/
public class GenericServerCipher extends ClientCipher {

    /**
     * {@code keySize} is instance that memorizes key size used in this communication session
     **/
    protected final KeySize keySize;

    /**
     * Constructor to init {@link GenericServerCipher}
     *
     * @param ivParameterSpec: initialization vector as {@link IvParameterSpec}
     * @param secretKey:       secret key used in the {@link Cipher} as {@link SecretKey}
     * @param algorithm:       algorithm used for AES cipher
     * @param keySize:         size in bits for {@link #secretKey}
     **/
    public GenericServerCipher(IvParameterSpec ivParameterSpec, SecretKey secretKey, Algorithm algorithm,
                               KeySize keySize) throws NoSuchPaddingException, NoSuchAlgorithmException {
        super(ivParameterSpec, secretKey, algorithm);
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
    public GenericServerCipher(String ivParameterSpec, String secretKey, Algorithm algorithm,
                               KeySize keySize) throws NoSuchPaddingException, NoSuchAlgorithmException {
        super(ivParameterSpec, secretKey, algorithm);
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
     **/
    public static SecretKey createSecretKey(KeySize keySize) throws NoSuchAlgorithmException {
        KeyGenerator keyGenerator = KeyGenerator.getInstance(AES_ALGORITHM_TYPE);
        keyGenerator.init(keySize.getSize());
        return keyGenerator.generateKey();
    }

    /**
     * This method is used to create a secret key
     *
     * @param keySize: size for secret key
     * @return secret key as {@link String}
     **/
    public static String createSecretKeyString(KeySize keySize) throws NoSuchAlgorithmException {
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
     * @return {@link #keySize} instance as {@link KeySize}
     **/
    public KeySize getKeySize() {
        return keySize;
    }

    /**
     * {@code KeySize} list of available key sizes
     **/
    public enum KeySize {

        /**
         * {@code k128} key of 128 bits length
         **/
        k128(128),

        /**
         * {@code k192} key of 192 bits length
         **/
        k192(192),

        /**
         * {@code k256} key of 256 bits length
         **/
        k256(256);

        /**
         * {@code size} of the key
         **/
        private final int size;

        /**
         * Constructor to init {@link KeySize}
         *
         * @param size: size of the key
         **/
        KeySize(int size) {
            this.size = size;
        }

        /**
         * This method is used to get {@link #size} instance <br>
         * Any params required
         *
         * @return {@link #size} instance as int
         **/
        public int getSize() {
            return size;
        }

        /**
         * This method is used to get {@link #size} instance <br>
         * Any params required
         *
         * @return {@link #size} instance as {@link String}
         **/
        @Override
        public String toString() {
            return String.valueOf(size);
        }

    }

}
