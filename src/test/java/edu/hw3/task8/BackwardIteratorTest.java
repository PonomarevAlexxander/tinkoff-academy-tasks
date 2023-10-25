package edu.hw3.task8;

import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class BackwardIteratorTest {
    @Test
    void test_BackwardIterator() {
        BackwardIterator<Integer> iterator = new BackwardIterator<>(List.of(1, 2, 3));
        assertThat(iterator.hasNext())
            .isTrue();
        assertThat(iterator.next())
            .isEqualTo(3);
        assertThat(iterator.hasNext())
            .isTrue();
        assertThat(iterator.next())
            .isEqualTo(2);
        assertThat(iterator.hasNext())
            .isTrue();
        assertThat(iterator.next())
            .isEqualTo(1);
        assertThat(iterator.hasNext())
            .isFalse();
    }
}
