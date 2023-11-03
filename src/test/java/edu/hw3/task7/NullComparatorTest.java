package edu.hw3.task7;

import java.util.TreeMap;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class NullComparatorTest {

    @Test
    void test_NullComparator() {
        TreeMap<String, String> tree = new TreeMap<>(new NullComparator<>());
        tree.put(null, "test");
        tree.put("someKey", "test");

        assertThat(tree.containsKey(null))
            .isTrue();
    }
}
