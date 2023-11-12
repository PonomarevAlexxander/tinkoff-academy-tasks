package edu.hw5.task3;

import java.time.LocalDate;
import java.util.Optional;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DateParserTest {
    @ParameterizedTest
    @MethodSource("provideDates")
    void test_parseDate(String date, Optional<LocalDate> expected) {
        assertThat(DateParser.parseDate(date))
            .isEqualTo(expected);
    }

    private static Stream<Arguments> provideDates() {
        return Stream.of(
            Arguments.of("2020-10-10", Optional.of(LocalDate.of(2020, 10, 10))),
            Arguments.of("2020.10.10", Optional.empty()),
            Arguments.of("2020-12-2", Optional.of(LocalDate.of(2020, 12, 2))),
            Arguments.of("1/3/1976", Optional.of(LocalDate.of(1976, 3, 1))),
            Arguments.of("1/3//1976", Optional.empty()),
            Arguments.of("1/3/20", Optional.of(LocalDate.of(20, 3, 1))),
            Arguments.of("tomorrow", Optional.of(LocalDate.now().plusDays(1))),
            Arguments.of("next day", Optional.empty()),
            Arguments.of("today", Optional.of(LocalDate.now()),
            Arguments.of("yesterday", Optional.of(LocalDate.now().minusDays(1))),
            Arguments.of("1 day ago",  Optional.of(LocalDate.now().minusDays(1))),
            Arguments.of("123 days after", Optional.empty()),
            Arguments.of("2234 days ago", Optional.of(LocalDate.now().minusDays(2234)))),
            Arguments.of(null, Optional.empty())
        );
    }
}
