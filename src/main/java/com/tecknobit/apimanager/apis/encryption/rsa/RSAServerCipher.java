package com.tecknobit.apimanager.apis.encryption.rsa;

import javax.crypto.NoSuchPaddingException;
import java.security.*;

import static com.tecknobit.apimanager.apis.encryption.BaseCipher.Algorithm.RSA_ALGORITHM;

public class RSAServerCipher extends RSAClientCipher {

    public enum RSAKeySize {

        LOW_STRENGTH_KEY(512),
        MEDIUM_STRENGTH_KEY(1024),
        HIGH_STRENGTH_KEY(2048),
        VERY_HIGH_STRENGTH_KEY(4096);

        private final int size;

        RSAKeySize(int size) {
            this.size = size;
        }

        public int getSize() {
            return size;
        }

        @Override
        public String toString() {
            return String.valueOf(size);
        }

    }

    private static KeyPair keyPairMatrix;

    public RSAServerCipher(KeyPair keyPair) throws NoSuchPaddingException, NoSuchAlgorithmException {
        this(keyPair.getPrivate(), keyPair.getPublic());
    }

    public RSAServerCipher(PrivateKey privateKey, PublicKey publicKey) throws NoSuchPaddingException, NoSuchAlgorithmException {
        super(privateKey, publicKey);
    }

    public static KeyPair generateRSAKeyPair(RSAKeySize keySize) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM.toString());
        keyPairGenerator.initialize(keySize.getSize(), new SecureRandom());
        return keyPairGenerator.generateKeyPair();
    }

    public static String generateBase64PrivateKey(RSAKeySize keySize) throws NoSuchAlgorithmException {
        return encodeBase64(generatePrivateKey(keySize).getEncoded());
    }

    public static PrivateKey generatePrivateKey(RSAKeySize keySize) throws NoSuchAlgorithmException {
        if (keyPairMatrix != null)
            throw new IllegalStateException("You must get the public key of the same pair first, then you can recreate a new key pair");
        keyPairMatrix = generateRSAKeyPair(keySize);
        return keyPairMatrix.getPrivate();
    }

    public static String generateBase64PublicKey() {
        return encodeBase64(generatePublicKey().getEncoded());
    }

    public static PublicKey generatePublicKey() {
        if (keyPairMatrix == null)
            throw new IllegalStateException("You must generate the private key first");
        PublicKey publicKey = keyPairMatrix.getPublic();
        keyPairMatrix = null;
        return publicKey;
    }

}
