package edu.hw5.task4;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PasswordValidatorTest {

    @ParameterizedTest
    @CsvSource({
        "123#567,true",
        "1234,false",
        "@goodone,true",
        ",false"
    })
    void test_isValid(String pass, boolean expected) {
        assertThat(PasswordValidator.isValid(pass))
            .isEqualTo(expected);
    }
}
