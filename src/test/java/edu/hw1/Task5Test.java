package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Task5Test {
    @ParameterizedTest
    @DisplayName("Test isPalindromeDescendant")
    @CsvSource({
        "11211230, true",
        "13001120, true",
        "23336014, true",
        "11, true",
        "9, false",
        "123312, true",
        "113, false"
    })
    void isPalindromeDescendantTest(int number, boolean expected) {
        assertThat(Task5.isPalindromeDescendant(number))
            .isEqualTo(expected);
    }
}
