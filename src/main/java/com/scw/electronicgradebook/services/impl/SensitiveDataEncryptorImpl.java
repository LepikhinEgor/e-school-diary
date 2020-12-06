package com.scw.electronicgradebook.services.impl;

import com.scw.electronicgradebook.services.interfaces.SensitiveDataEncryptor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.GCMParameterSpec;
import javax.crypto.spec.SecretKeySpec;
import java.io.File;
import java.io.FileInputStream;
import java.security.KeyStore;
import java.security.SecureRandom;
import java.security.interfaces.RSAPrivateKey;
import java.util.Arrays;
import java.util.Base64;

import static java.nio.charset.StandardCharsets.UTF_8;

@Slf4j
@Service
public class SensitiveDataEncryptorImpl implements SensitiveDataEncryptor {

    private static final String AES = "AES";

    private static final int IV_LENGTH = 12;

    @Value("${application.keystore.path}")
    String keystoreFile;
    @Value("${application.keystore.password}")
    String keystorePassword;
    @Value("${application.cipher.key.password}")
    String keyPassword;
    @Value("${application.cipher.key.alias}")
    String keyAlias;

    private RSAPrivateKey key;

    @PostConstruct
    private void init() {
        try {
            KeyStore keyStore = KeyStore.getInstance("JKS");
            keyStore.load(new FileInputStream(new File(keystoreFile)),
                    keystorePassword.toCharArray());

            if (!keyStore.containsAlias(keyAlias)) {
                throw new RuntimeException("Secret key not found");
            }

            this.key = (RSAPrivateKey) keyStore.getKey(keyAlias,
                    keyPassword.toCharArray());
        } catch (Exception e) {
            throw new RuntimeException("Error creating secret key", e);
        }
    }

    public String encrypt(String sensitive) {
        byte[] iv = generateIv();

        GCMParameterSpec gcmSpec = new GCMParameterSpec(128, iv);
        SecretKeySpec keySpec = new SecretKeySpec(key.getEncoded(), AES);

        try {
            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.ENCRYPT_MODE, keySpec, gcmSpec);

            byte[] encrypted = cipher.doFinal(sensitive.getBytes(UTF_8));
            return base64Encode(encrypted);
        } catch (Exception e) {
            log.error("Error during encryption", e);
            throw new IllegalArgumentException(e);
        }
    }

    public String decrypt(String input) {
        try {
            byte[] encrypted = base64Decode(input);
            byte[] iv = Arrays.copyOfRange(encrypted, 0, IV_LENGTH);

            var gcmSpec = new GCMParameterSpec(128, iv);
            var keySpec = new SecretKeySpec(key.getEncoded(), "AES");

            Cipher cipher = Cipher.getInstance("AES/GCM/NoPadding");
            cipher.init(Cipher.DECRYPT_MODE, keySpec, gcmSpec);

            byte[] decrypted = cipher.doFinal(encrypted, IV_LENGTH,
                    encrypted.length - IV_LENGTH);

            return new String(decrypted, UTF_8);
        } catch (Exception e) {
            log.error("Decryption error: ", e);
            throw new IllegalArgumentException(e);
        }
    }

    private String base64Encode(byte[] input) {
        return Base64.getEncoder().encodeToString(input);
    }

    private byte[] base64Decode(String input) {
        return Base64.getDecoder().decode(input);
    }

    private byte[] generateIv() {
        SecureRandom secureRandom = new SecureRandom();
        byte[] iv = new byte[IV_LENGTH];

        secureRandom.nextBytes(iv);

        return iv;
    }
}
