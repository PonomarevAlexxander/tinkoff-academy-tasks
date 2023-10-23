package edu.hw3.task3;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class FrequencyDictionary {
    public static <T> Map<T, Integer> freqDict(List<T> objects){
        Objects.requireNonNull(objects);
        Map<T, Integer> frequency = new HashMap<>();
        for (T object : objects) {
            int currentFreq = frequency.getOrDefault(object, 0);
            currentFreq++;
            frequency.put(object, currentFreq);
        }
        return frequency;
    }
}
