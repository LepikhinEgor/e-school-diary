package com.scw.electronicgradebook.services;

public interface SensitiveDataEncryptor {

    String encrypt(String sensitive);

    String decrypt(String encrypted);
}
