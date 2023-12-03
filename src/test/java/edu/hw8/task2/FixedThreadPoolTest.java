package edu.hw8.task2;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class FixedThreadPoolTest {
    @Test
    void test_FixedThreadPool_on_fibonacci_number() throws Exception {
        long[] fibNumbers = new long[22];
        try (ThreadPool pool = new FixedThreadPool(3)) {
            for (int i = 0; i < 22; i++) {
                int finalI = i;
                pool.execute(() -> {
                    fibNumbers[finalI] = fib(finalI);
                });
            }
            pool.start();
        }
        assertThat(fibNumbers)
            .isEqualTo(new long[]{0, 1, 1, 2, 3, 5, 8, 13, 21, 34, 55, 89, 144, 233, 377, 610, 987, 1597, 2584, 4181, 6765, 10946});
    }

    private long fib(long num) {
        if (num < 1) {
            return 0;
        }
        if (num == 1) {
            return 1;
        }
        return fib(num - 1) + fib(num - 2);
    }
}
