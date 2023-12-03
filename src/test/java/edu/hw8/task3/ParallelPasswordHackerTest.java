package edu.hw8.task3;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ParallelPasswordHackerTest {

    @ParameterizedTest
    @MethodSource("provideData")
    void test_hackPasswords(Map<String, String> hashed, Map<String, String> expected) {
        ParallelPasswordHacker hacker = new ParallelPasswordHacker();
        assertThat(hacker.hackPasswords(hashed, 10))
            .isEqualTo(expected);
    }

    private static Stream<Arguments> provideData() {
        Map<String, String> clearData = Map.of(
            "a.v.petrov", "12a34",
            "v.v.belov", "a1",
            "a.s.ivanov", "1a",
            "k.p.maslov", "Bcqq"
        );
        Map<String, String> hashedData = clearData.entrySet().stream()
            .collect(Collectors.toMap(Map.Entry::getKey, entry -> Md5HashEncoder.getMd5Hash(entry.getValue())));
        return Stream.of(Arguments.of(hashedData, clearData));
    }
}
