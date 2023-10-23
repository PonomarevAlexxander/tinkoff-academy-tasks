package edu.hw3.task3;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Stream;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class FrequencyDictionaryTest {
    @ParameterizedTest
    @MethodSource("provideFrequencyDictionary") <T>
    void test_FrequencyDictionary_on_normal_data(List<T> objects, Map<T, Integer> expected) {
        assertThat(FrequencyDictionary.freqDict(objects))
            .isEqualTo(expected);
    }

    private static Stream<Arguments> provideFrequencyDictionary() {
        return Stream.of(
            Arguments.of(new ArrayList<String>(){{
                add("a");
                add("a");
                add("bb");
                add("bb");
            }}, Map.of("a", 2, "bb", 2)),
            Arguments.of(new ArrayList<String>(){{
                add("код");
                add("код");
                add("код");
                add("bug");
            }}, Map.of("код", 3, "bug", 1)),
            Arguments.of(new ArrayList<Integer>(){{
                add(1);
                add(1);
                add(2);
                add(2);
            }}, Map.of(1, 2, 2, 2))
        );
    }
}
