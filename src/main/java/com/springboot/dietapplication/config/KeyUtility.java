package com.springboot.dietapplication.config;

import javax.crypto.Cipher;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;

public class KeyUtility {

    private static final String RSA = "RSA";

    public static PrivateKey loadPrivateKey()
            throws IOException, NoSuchAlgorithmException,
            InvalidKeySpecException {

        // Read Private Key.
        String path = "src/main/resources/key/private.key";
        File filePrivateKey = new File( path);
        FileInputStream fis = new FileInputStream( path);
        byte[] encodedPrivateKey = new byte[(int) filePrivateKey.length()];
        fis.read(encodedPrivateKey);
        fis.close();

        // Generate KeyPair.
        KeyFactory keyFactory = java.security.KeyFactory.getInstance(RSA);

        PKCS8EncodedKeySpec privateKeySpec = new PKCS8EncodedKeySpec(
                encodedPrivateKey);

        return keyFactory.generatePrivate(privateKeySpec);
    }

    public static String doRSADecryption(byte[] cipherText, PrivateKey privateKey) throws Exception {
        Cipher cipher = Cipher.getInstance(RSA);

        cipher.init(Cipher.DECRYPT_MODE, privateKey);

        byte[] result = cipher.doFinal(cipherText);

        return new String(result);
    }

}
