package com.mcformation.utils;

import java.util.regex.Pattern;

public class EmailUtils {
    private final static String EMAIL_PATTERN = "^(.+)@(\\S+)$";

    public static boolean validationEmail(String email) {
        return Pattern.compile(EMAIL_PATTERN).matcher(email).matches();
    }
}

