package edu.hw7.task1;

import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicLong;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class AtomicExampleTest {

    @Test
    void test_incrementBy() {
        AtomicExample.incrementBy(12);
        assertThat(AtomicExample.incrementBy(8))
            .isEqualTo(20);
        assertThat(AtomicExample.getCounter())
            .isInstanceOf(AtomicLong.class);
    }
}
