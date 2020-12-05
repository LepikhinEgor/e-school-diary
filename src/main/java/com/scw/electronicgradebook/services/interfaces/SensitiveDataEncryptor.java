package com.scw.electronicgradebook.services.interfaces;

public interface SensitiveDataEncryptor {

    String encrypt(String sensitive);

    String decrypt(String encrypted);
}
