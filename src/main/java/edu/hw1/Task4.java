package edu.hw1;

public class Task4 {

    private Task4() {
    }

    public static String fixString(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder fixedString = new StringBuilder();
        final int STEP_TO_NEXT_PAIR = 3;
        for (int i = 1; i < str.length(); i += (i % 2 == 1 ? -1 : STEP_TO_NEXT_PAIR)) {
            fixedString.append(str.charAt(i));
        }
        for (int i = fixedString.length(); i < str.length(); ++i) {
            fixedString.append(str.charAt(i));
        }
        return fixedString.toString();
    }
}
