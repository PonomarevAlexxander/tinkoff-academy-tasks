package edu.hw5.task5;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class PlateNumberValidator {
    private static final Pattern PLATE_NUMBER_PATTERN =
        Pattern.compile("^[АВЕКМНОРСТХ](?:00[1-9]|0[1-9][0-9]|[1-9][0-9][0-9])[АВЕКМНОРСТХ]{2}[1-9]?\\d{2}$");

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
