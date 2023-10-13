package edu.hw1;

import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Task1Test {
    @Test
    @DisplayName("Test minutesToSeconds")
    void minutesToSecondsTest() {
        Map<String, Integer> samples = new HashMap<>();
        samples.put("01:00", 60);
        samples.put("13:56", 836);
        samples.put("10:60", -1);
        samples.put("1012360", -1);
        samples.put("999:59", 59_999);
        samples.put("00:00", 0);

        for (Map.Entry<String, Integer> entry : samples.entrySet()) {
            Integer result = Task1.minutesToSeconds(entry.getKey());
            assertThat(result)
                .isEqualTo(entry.getValue());
        }
    }

    @ParameterizedTest
    @DisplayName("Test  minutesToSeconds on null data")
    @NullSource
    void minutesToSecondsOnNullTest(String data) {
        assertThrows(NullPointerException.class, () -> Task1.minutesToSeconds(data));
    }
}
