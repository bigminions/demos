package com.githup.bigminions.security;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class RsaDemo {

    private static KeyPair keyPair;

    private static String algorithm = "RSA";

    static {
        try {
            KeyPairGenerator keyPairGenerator = KeyPairGenerator.getInstance(algorithm);
            keyPair = keyPairGenerator.generateKeyPair();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            String data = "hello, world";

            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, keyPair.getPrivate());
            byte[] enData = cipher.doFinal(data.getBytes());
            ;
            System.out.println(new String(Base64.getEncoder().encode(enData)));

            cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, keyPair.getPublic());
            byte[] deData = cipher.doFinal(enData);
            System.out.println(new String(deData));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
    }


}
