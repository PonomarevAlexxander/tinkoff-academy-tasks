package edu.hw8.task3;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class Md5HashEncoderTest {

    @ParameterizedTest
    @CsvSource({
        "12345, 827ccb0eea8a706c4c34a16891f84e7b",
        "asbdfksadbf32485, 903cc372fd3c4799ce4d3e1d21ed7508",
        ",''"
    })
    void test_getMd5Hash(String data, String hash) {
        assertThat(Md5HashEncoder.getMd5Hash(data))
            .isEqualTo(hash);
    }
}
