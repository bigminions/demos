package com.githup.bigminions.security;

import javax.crypto.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;

public class AesDemo {

    private static SecretKey key;

    private static String algorithm = "AES";

    static {
        try {
            KeyGenerator keyGenerator = KeyGenerator.getInstance(algorithm);
            key = keyGenerator.generateKey();
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        try {
            String data = "hello, world";

            Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] enData = cipher.doFinal(data.getBytes());
            System.out.println(new String(Base64.getEncoder().encode(enData)));

            cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] deData = cipher.doFinal(enData);
            System.out.println(new String(deData));

        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        } catch (InvalidKeyException e) {
            e.printStackTrace();
        } catch (NoSuchPaddingException e) {
            e.printStackTrace();
        } catch (BadPaddingException e) {
            e.printStackTrace();
        } catch (IllegalBlockSizeException e) {
            e.printStackTrace();
        }
    }

}
