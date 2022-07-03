package com.mahanko.finalproject.util;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

/**
 * The PasswordEncryptor util class.
 * Perform password encryption using sha256 algorithm.
 */
public class PasswordEncryptor {

    private PasswordEncryptor() {
    }

    /**
     * Encrypt password using sha256 algorithm.
     *
     * @param password the password string.
     * @return the encrypted password.
     */
    public static String encrypt(String password) {
        return Hashing.sha256().hashBytes(password.getBytes(StandardCharsets.UTF_8)).toString();
    }
}
