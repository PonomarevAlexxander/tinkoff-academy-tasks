package edu.hw1;

public class Task4 {

    private Task4() {
    }

    //CHECKSTYLE:OFF: MagicNumber
    public static String fixString(String str) {
        if (str == null) {
            return null;
        }
        StringBuilder fixedString = new StringBuilder();
        for (int i = 1; i < str.length(); i += (i % 2 == 1 ? -1 : 3)) {
            fixedString.append(str.charAt(i));
        }
        for (int i = fixedString.length(); i < str.length(); ++i) {
            fixedString.append(str.charAt(i));
        }
        return fixedString.toString();
    }
    //CHECKSTYLE:ON: MagicNumber
}
