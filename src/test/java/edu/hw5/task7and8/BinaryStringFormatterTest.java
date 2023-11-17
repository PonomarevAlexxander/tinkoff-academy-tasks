package edu.hw5.task7and8;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class BinaryStringFormatterTest {

    @ParameterizedTest
    @CsvSource({
        "110,true",
        "000101010,true",
        "111,false",
        "10101010,false"
    })
    void hasMinimum3LettersWithLast0(String string, boolean expected) {
        assertThat(BinaryStringFormatter.hasMinimum3LettersWithLast0(string))
            .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
        "110,false",
        "000101010,true",
        "111,true",
        "101010101,true",
        "1,true",
        "0,true"
    })
    void lastLetterEqualsFirst(String string, boolean expected) {
        assertThat(BinaryStringFormatter.lastLetterEqualsFirst(string))
            .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
        "110,true",
        "000101010,false",
        "111,true",
        "1101,false",
        "1,true",
        "0,true"
    })
    void lengthFrom1To3(String string, boolean expected) {
        assertThat(BinaryStringFormatter.lengthFrom1To3(string))
            .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
        "111,true",
        "000101010,false",
        "1011101,true",
        "1101,false",
        "1,true",
        "0,false"
    })
    void everyOddLetterIs1(String string, boolean expected) {
        assertThat(BinaryStringFormatter.everyOddLetterIs1(string))
            .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
        "111,true",
        "1111101,true",
        "10110,true",
        "1101,false",
        "1,true",
        "0,true"
    })
    void hasOddLength(String string, boolean expected) {
        assertThat(BinaryStringFormatter.hasOddLength(string))
            .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
        "011,true",
        "01101101,false",
        "101101,true",
        "11011,false",
        "1,false",
        "0,true"
    })
    void hasLengthParityDifferentFromFirstLetter(String string, boolean expected) {
        assertThat(BinaryStringFormatter.hasLengthParityDifferentFromFirstLetter(string))
            .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
        "111,true",
        "01101101,true",
        "101101,false",
        "11011,false",
        "1000111011011100011110111,true",
        "0,false"
    })
    void hasZerosMultipleOf3(String string, boolean expected) {
        assertThat(BinaryStringFormatter.hasZerosMultipleOf3(string))
            .isEqualTo(expected);
    }
}
