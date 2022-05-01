package com.mahanko.finalproject.util;

import java.util.Base64;

public class CustomPictureEncoder {
    private CustomPictureEncoder() {
    }

    public static String arrayToBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    public static byte[] decodeByteArray(byte[] encodedBase64) {
        return Base64.getDecoder().decode(encodedBase64);
    }

    public static byte[] decodeString(String encodedBase64) {
        return Base64.getDecoder().decode(encodedBase64);
    }
}
