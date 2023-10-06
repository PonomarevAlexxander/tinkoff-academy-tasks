package edu.hw1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ArraysUtilsTest {
    @Test
    @DisplayName("Test isNestable on normal data")
    void isNestableTest() {
        final Map<Integer[][], Boolean> samples = new HashMap<>();
        samples.put(new Integer[][] {{1, 2, 3, 4}, {0, 6}}, true);
        samples.put(new Integer[][] {{3, 1}, {4, 0}}, true);
        samples.put(new Integer[][] {{9, 9, 8}, {8, 9}}, false);
        samples.put(new Integer[][] {{1, 2, 3, 4}, {2, 3}}, false);
        samples.put(new Integer[][] {{1, 2, 3, 4}, {3}}, false);
        samples.put(new Integer[][] {{1}, {0, 6}}, true);

        for (Map.Entry<Integer[][], Boolean> entry : samples.entrySet()) {
            int[] a = Arrays.stream(entry.getKey()[0])
                .mapToInt(Integer::intValue)
                .toArray();
            int[] b = Arrays.stream(entry.getKey()[1])
                .mapToInt(Integer::intValue)
                .toArray();
            Boolean result = ArraysUtils.isNestable(a, b);
            assertThat(result)
                .isEqualTo(entry.getValue());
        }
    }

    @Test
    @DisplayName("Test isNestable on null data")
    void isNullNestableTest() {
        Boolean result = ArraysUtils.isNestable(null, new int[] {1, 2, 3});
        assertThat(result)
            .isEqualTo(true);
        result = ArraysUtils.isNestable(new int[] {1, 2, 3}, null);
        assertThat(result)
            .isEqualTo(false);
        result = ArraysUtils.isNestable(null, null);
        assertThat(result)
            .isEqualTo(false);
    }
}
