package edu.hw5.task5;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class PlateNumberValidatorTest {

    @ParameterizedTest
    @CsvSource({
        "А990ВЕ099,false",
        "А990ВЕ199,true",
        "А001ВЕ99,true",
        "А000ВЕ99,false",
        "А123ВЕ777,true",
        "О777ОО177,true",
        "123АВЕ777,false",
        "А123ВГ77,false",
        "А123ВЕ7777,false",
        ",false"
    })
    void test_isValid(String plateNum, boolean expected) {
        assertThat(PlateNumberValidator.isValid(plateNum))
            .isEqualTo(expected);
    }
}
