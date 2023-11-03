package com.datasec;

import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.Base64;

public class Encryption {

    public static String encrypt(String in, PublicKey pk) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher encryptCipher = Cipher.getInstance("RSA");
        encryptCipher.init(Cipher.ENCRYPT_MODE, pk);
        byte[] inBytes = in.getBytes(StandardCharsets.UTF_8);
        byte[] encryptedInBytes = encryptCipher.doFinal(inBytes);
        String out = Base64.getEncoder().encodeToString(encryptedInBytes);
        return out;
    }

    public static String decrypt(String in, PrivateKey pk) throws NoSuchPaddingException, NoSuchAlgorithmException, InvalidKeyException, IllegalBlockSizeException, BadPaddingException {
        Cipher decryptCipher = Cipher.getInstance("RSA");
        decryptCipher.init(Cipher.DECRYPT_MODE, pk);
        byte[] inBytes = Base64.getDecoder().decode(in);
        byte[] decryptedInBytes = decryptCipher.doFinal(inBytes);
        String out = new String(decryptedInBytes, StandardCharsets.UTF_8);
        return out;
    }
}
