package com.myspring.demo.user.utils;

import java.security.SecureRandom;

public class SecurityUtils {

    public static byte[] randomBytes(int length) {
        byte[] bytes = new byte[length];
        SecureRandom secureRandom = new SecureRandom();
        secureRandom.nextBytes(bytes);
        return bytes;
    }
}