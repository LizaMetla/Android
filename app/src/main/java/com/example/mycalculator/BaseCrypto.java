package com.example.mycalculator;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

public class BaseCrypto {

    public static byte[] encryptMessageBase(byte[] message, byte[] keyBytes) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);
            return cipher.doFinal(message);
        } catch (Exception e) {
            return new byte[0];
        }

    }

    public static String decryptMessageBase(byte[] encryptedMessage, byte[] keyBytes) {
        try {
            Cipher cipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            SecretKey secretKey = new SecretKeySpec(keyBytes, "AES");
            cipher.init(Cipher.DECRYPT_MODE, secretKey);
            return new String(cipher.doFinal(encryptedMessage));
        } catch (Exception e) {
            return "";
        }

    }
}
