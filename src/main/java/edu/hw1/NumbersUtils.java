package edu.hw1;

import java.util.Arrays;

public class NumbersUtils {
    private NumbersUtils() {
    }

    //CHECKSTYLE:OFF: MagicNumber
    public static int countDigits(int number) {
        if (number < 10) {
            return 1;
        }
        return 1 + countDigits(number / 10);
    }

    public static boolean isPalindromeDescendant(int number) {
        String intView = String.valueOf(number);
        while (intView.length() > 1) {
            if (isPalindrome(intView)) {
                return true;
            }
            if (intView.length() % 2 != 0) {
                return false;
            }
            intView = getDescendant(intView);
        }
        return false;
    }

    public static int countK(int num) {
        if (countDigits(num) != 4) {
            throw new IllegalArgumentException("Number has to have 4 digits.");
        }
        return doCountK(num);
    }

    public static int rotateRight(int num, int nPositions) {
        if (num <= 0) {
            throw new IllegalArgumentException("Number should be positive");
        }

        int bitsToCode = (int) Math.ceil(Math.log(num + 1) / Math.log(2));
        int nShiftsRight = nPositions % bitsToCode;
        if (num == 1 || nShiftsRight == 0) {
            return num;
        }
        int nShiftsLeft = (bitsToCode - nShiftsRight) % bitsToCode;
        String bin = Integer.toBinaryString(num << nShiftsLeft).substring(nShiftsLeft);
        return (num >> nShiftsRight) | Integer.parseInt(bin, 2);
    }

    public static int rotateLeft(int num, int nPositions) {
        if (num <= 0) {
            throw new IllegalArgumentException("Number should be positive");
        }

        int bitsToCode = (int) Math.ceil(Math.log(num + 1) / Math.log(2));
        int nShiftsLeft = nPositions % bitsToCode;
        if (num == 1 || nShiftsLeft == 0) {
            return num;
        }
        int nShiftsRight = bitsToCode - nShiftsLeft;
        String bin = Integer.toBinaryString(num << nShiftsLeft).substring(nShiftsLeft);
        return (num >> nShiftsRight) | Integer.parseInt(bin, 2);
    }

    private static int doCountK(int num) {
        if (num == 6174) {
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
        for (int i = (arr.length - 1), ratio = 1; i >= 0; --i, ratio *= 10) {
            result += (arr[i] - '0') * ratio;
        }
        return result;
    }

    private static boolean isPalindrome(String intView) {
        assert intView != null;

        int begin = 0;
        int end = intView.length() - 1;
        while (begin < end) {
            if (intView.charAt(begin) != intView.charAt(end)) {
                return false;
            }
            ++begin;
            --end;
        }
        return true;
    }

    private static String getDescendant(String intView) {
        assert intView != null && intView.length() % 2 == 0;

        StringBuilder result = new StringBuilder();
        for (int i = 0; i < intView.length(); i += 2) {
            int a = intView.charAt(i) - '0';
            int b = intView.charAt(i + 1) - '0';
            result.append(a + b);
        }
        return result.toString();
    }
    //CHECKSTYLE:ON: MagicNumber
}
