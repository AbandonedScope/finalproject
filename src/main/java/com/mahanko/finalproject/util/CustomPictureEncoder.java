package com.mahanko.finalproject.util;

import java.util.Base64;

/**
 * The CustomPictureEncoder util class.
 * Perform byte arrays encoding base64 to strings.
 * Performs decoding strings encoded base64 to byte arrays;
 */
public class CustomPictureEncoder {
    private CustomPictureEncoder() {
    }

    /**
     * Encode byte array to string using base64.
     *
     * @param bytes the byte array.
     * @return the encoded base64 string.
     */
    public static String arrayToBase64(byte[] bytes) {
        return Base64.getEncoder().encodeToString(bytes);
    }

    /**
     * Decodes base64 encoded string ti byte array.
     *
     * @param encodedBase64 the encoded base64 string.
     * @return the decoded byte array.
     */
    public static byte[] decodeString(String encodedBase64) {
        return Base64.getDecoder().decode(encodedBase64);
    }
}
