package edu.hw3.task4;

import java.util.TreeMap;

public class RomanNumbers {

    @SuppressWarnings("MagicNumber")
    private static final TreeMap<Integer, String> ROMAN_NUMBERS = new TreeMap<>() {{
        put(1, "I");
        put(4, "IV");
        put(5, "V");
        put(9, "IX");
        put(10, "X");
        put(40, "XL");
        put(50, "L");
        put(90, "XC");
        put(100, "C");
        put(400, "CD");
        put(500, "D");
        put(900, "CM");
        put(1000, "M");
    }};

    private static final int MAX_ROMAN_NUMBER = 3999;
    private static final int MIN_ROMAN_NUMBER = 1;

    private RomanNumbers() {

    }

    public static String convertToRoman(int number) {
        if (number < MIN_ROMAN_NUMBER || number > MAX_ROMAN_NUMBER) {
            throw new IllegalArgumentException("number should be positive");
        }
        return getRoman(number);
    }

    private static String getRoman(int number) {
        var nearestEntry = ROMAN_NUMBERS.floorEntry(number);
        if (nearestEntry.getKey() == number) {
            return nearestEntry.getValue();
        }
        return nearestEntry.getValue() + getRoman(number - nearestEntry.getKey());
    }
}
