package com.mmt.email.securitykey;

import lombok.extern.slf4j.Slf4j;
import org.apache.commons.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESedeKeySpec;
import java.security.spec.KeySpec;

@Slf4j
@Component
public class TrippleDes {

    private static final String DESEDE_ENCRYPTION_SCHEME = "DESede";
    private static final String UNICODE_FORMAT = "UTF8";

    @Autowired
    private Environment environment;

    private byte[] arrayBytes;
    private SecretKey key;
    private KeySpec ks;
    private SecretKeyFactory skf;
    private Cipher cipher;
    private String myEncryptionKey;
    private String myEncryptionScheme;

    public String encrypt(String username, String unencryptedString) {
        this.initialize(username);
        String encryptedString = null;
        try {
            cipher.init(Cipher.ENCRYPT_MODE, key);
            byte[] plainText = unencryptedString.getBytes(UNICODE_FORMAT);
            byte[] encryptedText = cipher.doFinal(plainText);
            encryptedString = new String(Base64.encodeBase64(encryptedText));
        } catch (Exception e) {
            e.printStackTrace();
        }
        return encryptedString;
    }


    public String decrypt(String username, String encryptedString) {
        this.initialize(username);
        String decryptedText = null;
        try {
            cipher.init(Cipher.DECRYPT_MODE, key);
            byte[] encryptedText = Base64.decodeBase64(encryptedString);
            byte[] plainText = cipher.doFinal(encryptedText);
            decryptedText = new String(plainText);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return decryptedText;
    }

    private void initialize(String username) {
        /**
         * secretKeyIngredient should be changed at application.properties file
         * Exposing secretKeyIngredient in public will make your privateKey vulnerable to decryption.
         */
        String secretKeyIngredient = environment.getProperty("secretKeyIngredient");
        this.myEncryptionKey = username + secretKeyIngredient + username + secretKeyIngredient;
        myEncryptionScheme = DESEDE_ENCRYPTION_SCHEME;
        try {
            arrayBytes = myEncryptionKey.getBytes(UNICODE_FORMAT);
            ks = new DESedeKeySpec(arrayBytes);
            skf = SecretKeyFactory.getInstance(myEncryptionScheme);
            cipher = Cipher.getInstance(myEncryptionScheme);
            key = skf.generateSecret(ks);
        } catch (Exception e) {
            log.error(e.toString());
        }

    }

}
