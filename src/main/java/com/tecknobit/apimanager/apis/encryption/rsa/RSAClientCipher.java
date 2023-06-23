package com.tecknobit.apimanager.apis.encryption.rsa;

import com.tecknobit.apimanager.apis.encryption.BaseCipher;

import javax.crypto.NoSuchPaddingException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.X509EncodedKeySpec;

import static com.tecknobit.apimanager.apis.encryption.BaseCipher.Algorithm.RSA_ALGORITHM;
import static javax.crypto.Cipher.DECRYPT_MODE;
import static javax.crypto.Cipher.ENCRYPT_MODE;

public class RSAClientCipher extends BaseCipher {

    protected PrivateKey privateKey;

    protected PublicKey publicKey;

    public RSAClientCipher(String privateKey, String publicKey) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeySpecException {
        super(RSA_ALGORITHM);
        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(publicKey.getBytes());
        KeyFactory keyFactory = KeyFactory.getInstance("RSA");
        this.publicKey = keyFactory.generatePublic(keySpec);
        X509EncodedKeySpec keySpec1 = new X509EncodedKeySpec(publicKey.getBytes());
        KeyFactory keyFactory1 = KeyFactory.getInstance("RSA");
        this.privateKey = keyFactory1.generatePrivate(keySpec);
    }

    public RSAClientCipher(PrivateKey privateKey, PublicKey publicKey) throws NoSuchPaddingException, NoSuchAlgorithmException {
        super(RSA_ALGORITHM);
        this.privateKey = privateKey;
        this.publicKey = publicKey;
    }

    @Override
    public <T> byte[] encrypt(T content) throws Exception {
        cipher.init(ENCRYPT_MODE, publicKey);
        return super.encrypt(content);
    }

    @Override
    public <T> String decrypt(T content) throws Exception {
        cipher.init(DECRYPT_MODE, privateKey);
        return super.decrypt(content);
    }

    public PrivateKey getPrivateKey() {
        return privateKey;
    }

    public void setPrivateKey(PrivateKey privateKey) {
        this.privateKey = privateKey;
    }

    public PublicKey getPublicKey() {
        return publicKey;
    }

    public void setPublicKey(PublicKey publicKey) {
        this.publicKey = publicKey;
    }

}
