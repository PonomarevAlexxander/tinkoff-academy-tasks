package edu.hw3.task3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FrequencyDictionary {
    private FrequencyDictionary() {

    }

    public static <T> Map<T, Integer> freqDict(List<T> objects) {
        Objects.requireNonNull(objects);
        Map<T, Integer> frequency = new HashMap<>();
        for (T object : objects) {
            frequency.merge(object, 1, Integer::sum);
        }
        return frequency;
    }
}
