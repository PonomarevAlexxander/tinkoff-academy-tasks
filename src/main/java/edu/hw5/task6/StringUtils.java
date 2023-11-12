package edu.hw5.task6;

public class StringUtils {
    private StringUtils() {
    }

    public static boolean isSubstring(String string, String substring) {
        if (string == null || substring == null) {
            return false;
        }
        return string.matches(".*" + substring + ".*");
    }
}
