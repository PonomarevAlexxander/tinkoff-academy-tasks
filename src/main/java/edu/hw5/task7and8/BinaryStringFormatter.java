package edu.hw5.task7and8;

import java.util.regex.Pattern;

public class BinaryStringFormatter {
    private static final Pattern HAS_3_LETTERS_WITH_LAST_0 = Pattern.compile("^[01]{2}0[01]*$");
    private static final Pattern FIRST_EQUALS_LAST = Pattern.compile("^1[01]*1$|^0[01]*0$|^[10]$");
    private static final Pattern LENGTH_FROM_1_TO_3 = Pattern.compile("^[01]{1,3}$");

    // BONUS REGEX:
    private static final Pattern HAS_ODD_LENGTH = Pattern.compile("^([01]{2})*[01]$");
    private static final Pattern HAS_LENGTH_PARITY_DIFFERENT_FROM_FIRST_LETTER = Pattern
        .compile("^0([01]{2})*$|^1[01]([01]{2})*$");
    private static final Pattern ZEROS_ARE_MULTIPLE_OF_3 = Pattern.compile("^(1*01*01*01*)+$|^1*$");
    private static final Pattern EVERY_ODD_IS_1 = Pattern.compile("^(1[01])+$|^(1[01])+1$|^1$");

    private BinaryStringFormatter() {

    }

    public static boolean hasMinimum3LettersWithLast0(String string) {
        if (string == null) {
            return false;
        }
        return HAS_3_LETTERS_WITH_LAST_0.matcher(string).find();
    }

    public static boolean lastLetterEqualsFirst(String string) {
        if (string == null) {
            return false;
        }
        return FIRST_EQUALS_LAST.matcher(string).find();
    }

    public static boolean lengthFrom1To3(String string) {
        if (string == null) {
            return false;
        }
        return LENGTH_FROM_1_TO_3.matcher(string).find();
    }

    public static boolean everyOddLetterIs1(String string) {
        if (string == null) {
            return false;
        }
        return EVERY_ODD_IS_1.matcher(string).find();
    }

    public static boolean hasOddLength(String string) {
        if (string == null) {
            return false;
        }
        return HAS_ODD_LENGTH.matcher(string).find();
    }

    public static boolean hasLengthParityDifferentFromFirstLetter(String string) {
        if (string == null) {
            return false;
        }
        return HAS_LENGTH_PARITY_DIFFERENT_FROM_FIRST_LETTER.matcher(string).find();
    }

    public static boolean hasZerosMultipleOf3(String string) {
        if (string == null) {
            return false;
        }
        return ZEROS_ARE_MULTIPLE_OF_3.matcher(string).find();
    }
}
