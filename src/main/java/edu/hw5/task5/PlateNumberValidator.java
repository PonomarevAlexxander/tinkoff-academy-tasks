package edu.hw5.task5;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlateNumberValidator {
    private static final Pattern PLATE_NUMBER_PATTERN = Pattern.compile("^[АВЕКМНОРСТХ]\\d{1,2}[1-9][АВЕКМНОРСТХ]{2}\\d{2,3}$");

    private PlateNumberValidator() {
    }

    public static boolean isValid(String plateNumber) {
        if (plateNumber == null) {
            return false;
        }
        Matcher matcher = PLATE_NUMBER_PATTERN.matcher(plateNumber);
        return matcher.find();
    }
}
