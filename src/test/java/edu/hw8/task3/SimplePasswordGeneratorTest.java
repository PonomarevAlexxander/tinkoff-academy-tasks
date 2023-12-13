package edu.hw8.task3;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SimplePasswordGeneratorTest {

    @ParameterizedTest
    @CsvSource({
        "aaaa, aaab",
        "8888, 8889",
        "aaaabb, aaaabc",
        ", 0",
        "bcda8Az, bcda8B0"
    })
    void test_generateNextPassword(String pass, String expected) {
        SimplePasswordGenerator generator = new SimplePasswordGenerator();
        assertThat(generator.generateNextPassword(pass))
            .isEqualTo(expected);
    }
}
