package edu.hw5.task6;

import java.util.regex.Pattern;

public class StringUtils {
    private StringUtils() {
    }

    public static boolean isSubstring(String string, String substring) {
        if (string == null || substring == null) {
            return false;
        }
        return string.matches(".*" + Pattern.quote(substring) + ".*");
    }
}
