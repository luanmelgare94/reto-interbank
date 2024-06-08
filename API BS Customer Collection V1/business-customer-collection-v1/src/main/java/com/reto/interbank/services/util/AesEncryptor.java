package com.reto.interbank.services.util;

import java.security.Key;
import java.util.Base64;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

public class AesEncryptor {

    private static final String ALGORITHM = "AES";
    private final Key key;

    public AesEncryptor(String secret) {
        this.key = new SecretKeySpec(secret.getBytes(), ALGORITHM);
    }

    public String encrypt(String value) throws Exception {
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.ENCRYPT_MODE, key);
        byte[] encryptedByteValue = cipher.doFinal(value.getBytes("utf-8"));
        return Base64.getEncoder().encodeToString(encryptedByteValue);

    }

    public String decrypt(String value) throws Exception {
        String cleanValue = value.trim().replace(" ", "");
        byte[] decryptedValue64 = Base64.getDecoder().decode(cleanValue);
        Cipher cipher = Cipher.getInstance(ALGORITHM);
        cipher.init(Cipher.DECRYPT_MODE, key);
        byte[] decryptedByteValue = cipher.doFinal(decryptedValue64);
        return new String(decryptedByteValue, "utf-8");
    }

}