package edu.hw1;

public class Task2 {
    private static final int BASE = 10;

    private Task2() {
    }

    public static int countDigits(int number) {
        if (number < BASE) {
            return 1;
        }
        return 1 + countDigits(number / BASE);
    }
}
