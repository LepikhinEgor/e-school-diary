package com.scw.electronicgradebook.services.impl;

import com.scw.electronicgradebook.services.interfaces.SensitiveDataEncryptor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.encrypt.BytesEncryptor;
import org.springframework.security.crypto.encrypt.Encryptors;
import org.springframework.stereotype.Service;

import java.util.Base64;

@Service
public class SensitiveDataEncryptorImpl implements SensitiveDataEncryptor {

    @Value("${application.encryption.password}")
    private String secretKey;

    @Value("${application.encryption.salt}")
    private String salt;

    @Override
    public String encrypt(String sensitive) {
        BytesEncryptor encryptor = Encryptors.standard(secretKey, salt);

        byte[] encrypted = encryptor.encrypt(sensitive.getBytes());

        return Base64.getEncoder().encodeToString(encrypted);
    }

    @Override
    public String decrypt(String encrypted) {
        BytesEncryptor encryptor = Encryptors.standard(secretKey, salt);

        byte[] base64Encoded = Base64.getDecoder().decode(encrypted.getBytes());
        byte[] sensitive = encryptor.decrypt(base64Encoded);

        return new String(sensitive);
    }
}
