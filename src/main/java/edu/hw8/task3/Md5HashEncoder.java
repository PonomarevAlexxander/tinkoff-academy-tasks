package edu.hw8.task3;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Md5HashEncoder {
    private static final MessageDigest MD;

    static {
        try {
            MD = MessageDigest.getInstance("MD5");
        } catch (NoSuchAlgorithmException e) {
            throw new RuntimeException(e);
        }
    }

    private Md5HashEncoder() {
    }

    public static String getMd5Hash(String pass) {
        if (pass == null || pass.isBlank()) {
            return "";
        }
        byte[] hash = MD.digest(pass.getBytes());
        return bytesToHex(hash);
    }

    private static String bytesToHex(byte[] bytes) {
        StringBuilder out = new StringBuilder();
        for (byte b : bytes) {
            out.append(String.format("%02x", b));
        }
        return out.toString();
    }
}
