package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Task6Test {

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
        assertThat(Task6.countK(number))
            .isEqualTo(expected);
    }
}
