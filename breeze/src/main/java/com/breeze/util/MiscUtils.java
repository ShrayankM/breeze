package com.breeze.util;

import org.apache.commons.lang3.RandomStringUtils;

import java.util.Objects;

public class MiscUtils {

    public static String generateCodeForEntity(String prefix, int length) {
        if (prefix == null || prefix.isEmpty()) {
            prefix = "";
        }

        int finalLength = length - prefix.length();
        String randomString = RandomStringUtils.random(finalLength, false, true);
        return (prefix + randomString).toUpperCase();
    }

    public static boolean isStringNullOrEmpty(String value) {
        if (Objects.isNull(value) || value.isBlank()) {
            return true;
        }
        return false;
    }

}
