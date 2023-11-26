package edu.hw7.task2;

import java.math.BigInteger;
import java.util.stream.IntStream;

public class Factorial {
    private Factorial() {
    }

    public static BigInteger factorial(int number) {
        if (number < 0) {
            throw new IllegalArgumentException("number should be positive");
        }
        if (number < 2) {
            return BigInteger.ONE;
        }

        return IntStream
            .range(2, number + 1)
            .mapToObj(BigInteger::valueOf)
            .reduce(BigInteger::multiply)
            .get();
    }
}
