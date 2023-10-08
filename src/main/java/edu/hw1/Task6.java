package edu.hw1;

import java.util.Arrays;

public class Task6 {
    private static final int KAPREKAR_CONSTANT = 6174;

    private Task6() {
    }

    //CHECKSTYLE:OFF: MagicNumber
    public static int countK(int num) {
        if (Task2.countDigits(num) != 4) {
            throw new IllegalArgumentException("Number has to have 4 digits.");
        }
        return doCountK(num);
    }
    //CHECKSTYLE:ON: MagicNumber

    private static int doCountK(int num) {
        if (num == KAPREKAR_CONSTANT) {
            return 0;
        }
        char[] ascending = String.valueOf(num).toCharArray();
        Arrays.sort(ascending);
        char[] descending = Arrays.copyOf(ascending, ascending.length);
        reverseArray(descending);

        return 1 + doCountK(convertToNumber(descending) - convertToNumber(ascending));
    }

    private static void reverseArray(char[] arr) {
        int begin = 0;
        int end = arr.length - 1;
        while (begin < end) {
            char temp = arr[begin];
            arr[begin] = arr[end];
            arr[end] = temp;

            ++begin;
            --end;
        }
    }

    private static int convertToNumber(char[] arr) {
        int result = 0;
        final int BASE = 10;
        for (int i = (arr.length - 1), ratio = 1; i >= 0; --i, ratio *= BASE) {
            result += (arr[i] - '0') * ratio;
        }
        return result;
    }
}
