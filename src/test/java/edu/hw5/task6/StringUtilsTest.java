package edu.hw5.task6;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class StringUtilsTest {

    @ParameterizedTest
    @CsvSource({
        "achfdbaabgabcaabg,abc,true",
        "hellobuddies,hell,true",
        "assf\\daasda,f\\d,true",
        ",hi,false",
        "some,,false"
    })
    void test_isSubstring(String string, String substring, boolean expected) {
        assertThat(StringUtils.isSubstring(string, substring))
            .isEqualTo(expected);
    }
}
