package edu.hw1;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertNotSame;

public class StringUtilsTest {
    @ParameterizedTest
    @DisplayName("Test fixString")
    @CsvSource({
        "123456, 214365",
        "'hTsii  s aimex dpus rtni.g', 'This is a mixed up string.'",
        "badce, abcde",
        "'', ''",
        ","
    })
    void fixStringTest(String data, String expectedAnswer) {
        String result = StringUtils.fixString(data);
        assertThat(result)
            .isEqualTo(expectedAnswer);
        if (data != null) {
            assertNotSame(result, data);
        }
    }

}
