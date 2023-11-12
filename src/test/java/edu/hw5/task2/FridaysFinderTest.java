package edu.hw5.task2;

import java.time.LocalDate;
import java.util.List;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class FridaysFinderTest {

    @Test
    void test_findAllFriday13th_with_normal_data() {
        List<LocalDate> result = FridaysFinder.findAllFriday13th(2024);
        assertThat(result)
            .isEqualTo(List.of(
                LocalDate.of(2024, 9, 13),
                LocalDate.of(2024, 12, 13)
            ));
    }

    @ParameterizedTest
    @MethodSource("provideDataFor_test_getNextFriday13th")
    void test_getNextFriday13th(LocalDate date, LocalDate expected) {
        assertThat(FridaysFinder.getNextFriday13th(date))
            .isEqualTo(expected);
    }

    private static Stream<Arguments> provideDataFor_test_getNextFriday13th() {
        return Stream.of(
            Arguments.of(LocalDate.of(2024, 9, 1), LocalDate.of(2024, 9, 13)),
            Arguments.of(LocalDate.of(2023, 12, 31), LocalDate.of(2024, 9, 13)),
            Arguments.of(LocalDate.of(1925, 1, 1), LocalDate.of(1925, 2, 13))
        );
    }
}
