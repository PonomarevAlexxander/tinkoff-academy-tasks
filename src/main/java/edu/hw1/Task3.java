package edu.hw1;

import java.util.Arrays;
import java.util.OptionalInt;

public class Task3 {

    private Task3() {
    }

    public static boolean isNestable(int[] array, int[] container) {
        if (container == null || container.length < 2) {
            return false;
        }
        if (array == null) {
            return true;
        }
        OptionalInt min = Arrays
            .stream(container)
            .min();
        OptionalInt max = Arrays
            .stream(container)
            .max();
        return Arrays
            .stream(array)
            .allMatch(num -> min.getAsInt() < num && num < max.getAsInt());
    }
}
