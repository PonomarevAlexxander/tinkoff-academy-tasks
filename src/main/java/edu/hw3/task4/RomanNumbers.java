package edu.hw3.task4;

import java.util.TreeMap;

public class RomanNumbers {
    private static final TreeMap<Integer, String> romanNumbers = new TreeMap<>() {{
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

    public static String convertToRoman(int number) {
        if (number < 1 || number > 3999) {
            throw new IllegalArgumentException("number should be positive");
        }
        return getRoman(number);
    }

    private static String getRoman(int number) {
        var nearestEntry = romanNumbers.floorEntry(number);
        if (nearestEntry.getKey() == number) {
            return nearestEntry.getValue();
        }
        return nearestEntry.getValue() + getRoman(number - nearestEntry.getKey());
    }
}
