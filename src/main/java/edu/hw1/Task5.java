package edu.hw1;

public class Task5 {
    private Task5() {
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
}
