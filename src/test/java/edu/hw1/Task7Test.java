package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Task7Test {

    @ParameterizedTest
    @DisplayName("Test rotateRight")
    @CsvSource({
        "8, 1, 4",
        "1, 5, 1",
        "9, 1, 12"
    })
    void rotateRightTest(int number, int pos, int expected) {
        assertThat(Task7.rotateRight(number, pos))
            .isEqualTo(expected);
    }

    @ParameterizedTest
    @DisplayName("Test rotateLeft")
    @CsvSource({
        "16, 1, 1",
        "17, 2, 6"
    })
    void rotateLeftTest(int number, int pos, int expected) {
        assertThat(Task7.rotateLeft(number, pos))
            .isEqualTo(expected);
    }
}
