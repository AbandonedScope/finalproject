package com.mahanko.finalproject.util;

import com.google.common.hash.Hashing;

import java.nio.charset.StandardCharsets;

public class PasswordEncryptor {

    private PasswordEncryptor() {
    }

    public static String encrypt(String password) {
        return Hashing.sha256().hashBytes(password.getBytes(StandardCharsets.UTF_8)).toString();
    }
}
