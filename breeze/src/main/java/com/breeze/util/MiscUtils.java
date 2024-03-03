package com.breeze.util;

import org.apache.commons.lang3.RandomStringUtils;

public class MiscUtils {

    public static String generateCodeForEntity(String prefix, int length) {
        if (prefix == null || prefix.isEmpty()) {
            prefix = "";
        }

        int finalLength = length - prefix.length();
        String randomString = RandomStringUtils.random(finalLength, false, true);
        return (prefix + randomString).toUpperCase();
    }

}
