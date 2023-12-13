package edu.hw8.task3;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SequentialPasswordHackerTest {
    @ParameterizedTest
    @MethodSource("provideData")
    void test_hackPasswords(Map<String, String> hashed, Map<String, String> expected) {
        SequentialPasswordHacker hacker = new SequentialPasswordHacker();
        assertThat(hacker.hackPasswords(hashed))
            .isEqualTo(expected);
    }

    private static Stream<Arguments> provideData() {
        Map<String, String> clearData = Map.of(
            "a.v.petrov", "1a3",
            "v.v.belov", "a1",
            "a.s.ivanov", "1a",
            "k.p.maslov", "2"
        );
        Map<String, String> hashedData = clearData.entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getKey, entry -> Md5HashEncoder.getMd5Hash(entry.getValue())));
        return Stream.of(Arguments.of(hashedData, clearData));
    }
}
