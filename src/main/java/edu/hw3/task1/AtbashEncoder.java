package edu.hw3.task1;

import java.util.Objects;

public class AtbashEncoder {
    public static String atbash(String sentence) {
        Objects.requireNonNull(sentence);
        StringBuilder result = new StringBuilder(sentence.length());
        for (int index = 0; index < sentence.length(); ++index) {
            result.append(mirrowLetter(sentence.charAt(index)));
        }
        return result.toString();
    }

    private static char mirrowLetter(char letter) {
        boolean isLowered = Character.isLowerCase(letter);
        char lowered = Character.toLowerCase(letter);
        if (lowered > 'z' || lowered < 'a') {
            return letter;
        }

        char mirrowed = (char) ('z' - (lowered - 'a'));
        return isLowered ? mirrowed : Character.toUpperCase(mirrowed);
    }
}
