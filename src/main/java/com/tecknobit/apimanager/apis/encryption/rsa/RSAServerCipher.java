package com.tecknobit.apimanager.apis.encryption.rsa;

import com.tecknobit.apimanager.apis.encryption.BaseCipher;

import java.security.*;
import java.util.Base64;

import static com.tecknobit.apimanager.apis.encryption.BaseCipher.Algorithm.RSA_ALGORITHM;

/**
 * The {@code RSAServerCipher} class is useful for server side applications for {@code "RSA"}'s encryption or decryption
 *
 * @author Tecknobit N7ghtm4r3
 * @apiNote see the usage at <a href="https://github.com/N7ghtm4r3/APIManager/blob/main/documd/RSA.md">RSA.md</a>
 * @see BaseCipher
 * @see RSAClientCipher
 * @since 2.1.5
 */
public class RSAServerCipher extends RSAClientCipher {

    /**
     * {@code RSAKeyStrength} list of available key strengths
     */
    public enum RSAKeyStrength {

        /**
         * {@code LOW_STRENGTH_KEY} strength
         */
        LOW_STRENGTH_KEY(512),
        /**
         * {@code MEDIUM_STRENGTH_KEY} strength
         */
        MEDIUM_STRENGTH_KEY(1024),
        /**
         * {@code HIGH_STRENGTH_KEY} strength
         */
        HIGH_STRENGTH_KEY(2048),
        /**
         * {@code VERY_HIGH_STRENGTH_KEY} strength
         */
        VERY_HIGH_STRENGTH_KEY(4096);

        /**
         * {@code strength} value
         */
        private final int strength;

        /**
         * Constructor to init a {@link RSAKeyStrength} object
         *
         * @param strength: strength value
         */
        RSAKeyStrength(int strength) {
            this.strength = strength;
        }

        /**
         * Method to get {@link #strength} instance <br>
         * No-any params required
         *
         * @return {@link #strength} instance as int
         */
        public int getStrength() {
            return strength;
        }

        /**
         * Returns a string representation of the object <br>
         * No-any params required
         *
         * @return a string representation of the object as {@link String}
         */
        @Override
        public String toString() {
            return String.valueOf(strength);
        }

    }

    /**
     * {@code keyPairMatrix} used to generate and manage the keys of the same pair
     */
    private static KeyPair keyPairMatrix;

    /**
     * Constructor to init a {@link RSAServerCipher} object
     *
     * @param privateKey: the private key used to decrypt the content
     * @param publicKey:  the public key used to encrypt the content
     */
    public RSAServerCipher(String privateKey, String publicKey) throws Exception {
        super(privateKey, publicKey);
    }

    /**
     * Constructor to init a {@link RSAServerCipher} object
     *
     * @param keyPair: key pair from fetch the private and the public key
     */
    public RSAServerCipher(KeyPair keyPair) throws Exception {
        this(keyPair.getPrivate(), keyPair.getPublic());
    }

    /**
     * Constructor to init a {@link RSAServerCipher} object
     *
     * @param privateKey: the private key used to decrypt the content
     * @param publicKey:  the public key used to encrypt the content
     */
    public RSAServerCipher(PrivateKey privateKey, PublicKey publicKey) throws Exception {
        super(privateKey, publicKey);
    }

    /**
     * Method to generate the RSA key pair
     *
     * @param keyStrength: strength of the keys to generate
     * @return key pair with the keys generated as {@link KeyPair}
     */
    public static KeyPair generateRSAKeyPair(RSAKeyStrength keyStrength) throws NoSuchAlgorithmException {
        KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(RSA_ALGORITHM.toString());
        keyPairGenerator.initialize(keyStrength.getStrength(), new SecureRandom());
        return keyPairGenerator.generateKeyPair();
    }

    /**
     * Method to generate an RSA private key
     *
     * @param keyStrength: strength of the private key to generate
     * @return RSA private key as {@link Base64}-{@link String}
     * @apiNote to create a new private key you must invoke first the {@link #generatePublicKey()}
     * or {@link #generateBase64PublicKey()} method to have the keys of the same pair
     */
    public static String generateBase64PrivateKey(RSAKeyStrength keyStrength) throws NoSuchAlgorithmException {
        return encodeBase64(generatePrivateKey(keyStrength).getEncoded());
    }

    /**
     * Method to generate an RSA private key
     *
     * @param keyStrength: strength of the private key to generate
     * @return RSA private key as {@link PrivateKey}
     * @apiNote to create a new private key you must invoke first the {@link #generatePublicKey()}
     * or {@link #generateBase64PublicKey()} method to have the keys of the same pair
     */
    public static PrivateKey generatePrivateKey(RSAKeyStrength keyStrength) throws NoSuchAlgorithmException {
        if (keyPairMatrix != null)
            throw new IllegalStateException("You must get the public key of the same pair first, then you can recreate a new key pair");
        keyPairMatrix = generateRSAKeyPair(keyStrength);
        return keyPairMatrix.getPrivate();
    }

    /**
     * Method to generate an RSA public key <br>
     * No-any params required
     *
     * @return RSA public key as {@link Base64}-{@link String}
     * @apiNote to create a public key you must invoke first the {@link #generatePrivateKey(RSAKeyStrength)}
     * or {@link #generateBase64PrivateKey(RSAKeyStrength)}} method to have the keys of the same pair
     */
    public static String generateBase64PublicKey() {
        return encodeBase64(generatePublicKey().getEncoded());
    }

    /**
     * Method to generate an RSA public key <br>
     * No-any params required
     *
     * @return RSA public key as {@link PublicKey}
     * @apiNote to create a public key you must invoke first the {@link #generatePrivateKey(RSAKeyStrength)}
     * or {@link #generateBase64PrivateKey(RSAKeyStrength)}} method to have the keys of the same pair
     */
    public static PublicKey generatePublicKey() {
        if (keyPairMatrix == null)
            throw new IllegalStateException("You must generate the private key first");
        PublicKey publicKey = keyPairMatrix.getPublic();
        keyPairMatrix = null;
        return publicKey;
    }

}
