package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class NumbersUtilsTest {
    @ParameterizedTest
    @DisplayName("Test countDigits")
    @ValueSource(ints = {4666, 544, 0})
    void countDigitsTest(int number) {
        assertThat(NumbersUtils.countDigits(number))
            .isEqualTo(String.valueOf(number).length());
    }

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
        assertThat(NumbersUtils.isPalindromeDescendant(number))
            .isEqualTo(expected);
    }

    @ParameterizedTest
    @DisplayName("Test countK")
    @CsvSource({
        "3524, 3",
        "6621, 5",
        "6554, 4",
        "1234, 3",
        "6174, 0"
    })
    void countKTest(int number, int expected) {
        assertThat(NumbersUtils.countK(number))
            .isEqualTo(expected);
    }

    @ParameterizedTest
    @DisplayName("Test rotateRight")
    @CsvSource({
        "8, 1, 4",
        "1, 5, 1",
        "9, 1, 12"
    })
    void rotateRightTest(int number, int pos, int expected) {
        assertThat(NumbersUtils.rotateRight(number, pos))
            .isEqualTo(expected);
    }

    @ParameterizedTest
    @DisplayName("Test rotateLeft")
    @CsvSource({
        "16, 1, 1",
        "17, 2, 6"
    })
    void rotateLeftTest(int number, int pos, int expected) {
        assertThat(NumbersUtils.rotateLeft(number, pos))
            .isEqualTo(expected);
    }
}
