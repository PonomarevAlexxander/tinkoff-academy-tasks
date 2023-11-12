package edu.hw5.task4;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PasswordValidator {
    private static final Pattern PASSWORD_PATTERN = Pattern.compile(".*[~!@#$%^&*|].*");

    private PasswordValidator() {
    }

    public static boolean isValid(String password) {
        if (password == null) {
            return false;
        }
        Matcher matcher = PASSWORD_PATTERN.matcher(password);
        return matcher.find();
    }
}
