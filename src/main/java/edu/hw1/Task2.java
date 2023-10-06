package edu.hw1;

public class Task2 {
    private Task2() {
    }

    //CHECKSTYLE:OFF: MagicNumber
    public static int countDigits(int number) {
        if (number < 10) {
            return 1;
        }
        return 1 + countDigits(number / 10);
    }
    //CHECKSTYLE:ON: MagicNumber
}
