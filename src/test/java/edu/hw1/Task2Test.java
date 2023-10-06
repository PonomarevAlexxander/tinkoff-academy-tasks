package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class Task2Test {
    @ParameterizedTest
    @DisplayName("Test countDigits")
    @ValueSource(ints = {4666, 544, 0})
    void countDigitsTest(int number) {
        assertThat(Task2.countDigits(number))
            .isEqualTo(String.valueOf(number).length());
    }
}
