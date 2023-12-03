package edu.hw8.task3;

import java.util.TreeSet;

public class SimplePasswordGenerator implements PasswordGenerator {
    private final TreeSet<Character> alphabet = new TreeSet<>() {{
        for (char number = '0'; number <= '9'; number++) {
            add(number);
        }
        for (char letter = 'a'; letter <= 'z'; letter++) {
            add(letter);
            add(Character.toUpperCase(letter));
        }
    }};

    public String generateNextPassword(String pass) {
        if (pass == null || pass.isBlank()) {
            return alphabet.first().toString();
        }

        StringBuilder builder = new StringBuilder(pass);
        for (int index = builder.length() - 1; index >= 0; index--) {
            Character next = alphabet.higher(builder.charAt(index));
            if (next != null) {
                builder.setCharAt(index, next);
                return builder.toString();
            }
            if (index == 0) {
                return alphabet.first().toString().repeat(builder.length() + 1);
            }
            builder.setCharAt(index, alphabet.first());
        }
        return builder.toString();
    }

    public TreeSet<Character> getAlphabet() {
        return alphabet;
    }
}
