package edu.hw3.task2;

import java.util.LinkedList;
import java.util.List;
import java.util.Objects;

public class BracketsClustering {
    public static List<String> clusterize(String expression) {
        Objects.requireNonNull(expression);

        List<String> result = new LinkedList<>();
        int stabilizer = 0;
        int clusterLength = 0;
        for (int index = 0; index < expression.length(); ++index) {
            char bracket = expression.charAt(index);
            if (bracket == '(') {
                stabilizer++;
                clusterLength++;
            } else if (bracket == ')') {
                stabilizer--;
                clusterLength++;
            }
            if (stabilizer < 0) {
                throw new IllegalArgumentException("String has wrong brackets parentheses");
            }
            if (stabilizer == 0 && clusterLength != 0) {
                result.add(expression.substring(index - clusterLength + 1, index + 1));
                clusterLength = 0;
            }
        }
        if (stabilizer > 0) {
            throw new IllegalArgumentException("String has wrong brackets parentheses");
        }
        return result;
    }
}
