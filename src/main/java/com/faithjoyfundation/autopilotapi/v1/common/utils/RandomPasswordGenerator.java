package com.faithjoyfundation.autopilotapi.v1.common.utils;

import java.security.SecureRandom;

public class RandomPasswordGenerator {

    private static final String ALPHA_NUMERIC_STRING = "ABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789abcdefghijklmnopqrstuvwxyz";

    private static final SecureRandom RANDOM = new SecureRandom();

    public static String generate(int length) {
        StringBuilder tempPassword = new StringBuilder();
        for (int i = 0; i < length; i++) {
            int index = RANDOM.nextInt(ALPHA_NUMERIC_STRING.length());
            tempPassword.append(ALPHA_NUMERIC_STRING.charAt(index));
        }
        return  tempPassword.toString();
    }
}
