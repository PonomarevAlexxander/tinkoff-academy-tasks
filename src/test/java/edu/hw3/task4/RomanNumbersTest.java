package edu.hw3.task4;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class RomanNumbersTest {
    @ParameterizedTest
    @CsvSource({
        "2, 'II'",
        "12, 'XII'",
        "16, 'XVI'",
        "3999, 'MMMCMXCIX'"
    })
    void test_RomanNumbers_convertToRoman_on_normal_data(int number, String expected) {
        assertThat(RomanNumbers.convertToRoman(number))
            .isEqualTo(expected);
    }

    @ParameterizedTest
    @ValueSource(ints = {-1, 0, 4000, 10000})
    void test_RomanNumbers_convertToRoman_on_illegal_data(int number) {
        assertThatThrownBy(() -> RomanNumbers.convertToRoman(number))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
