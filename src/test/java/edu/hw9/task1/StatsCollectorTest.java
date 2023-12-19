package edu.hw9.task1;

import org.junit.jupiter.api.Test;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class StatsCollectorTest {
    @Test
    void test_StatsCollector_multithread() throws InterruptedException {
        try (StatsCollector collector = new StatsCollector(4);
             ExecutorService service = Executors.newFixedThreadPool(4)) {
            CountDownLatch latch = new CountDownLatch(4);
            for (int thread = 0; thread < 4; thread++) {
                service.submit(() -> {
                    for (int i = 0; i < 20; i++) {
                        collector.push("sum", new double[] {11, 11, 11, 11, 11});
                    }
                    latch.countDown();
                });
            }
            latch.await();
            TimeUnit.SECONDS.sleep(1);
            Map<String, Double> stats = collector.stats();
            assertThat(stats.get("sum"))
                .isNotNull();
        }
    }

    @Test
    void test_StatsCollector_min() throws InterruptedException {
        try (StatsCollector collector = new StatsCollector(4)) {
            collector.push("min", new double[] {1, -2, -56, 0, 12});
            TimeUnit.SECONDS.sleep(1);
            assertThat(collector.stats().get("min"))
                .isEqualTo(-56);
        }
    }

    @Test
    void test_StatsCollector_max() throws InterruptedException {
        try (StatsCollector collector = new StatsCollector(4)) {
            collector.push("max", new double[] {1, -2, 56, 0, 12});
            TimeUnit.SECONDS.sleep(1);
            assertThat(collector.stats().get("max"))
                .isEqualTo(56);
        }
    }

    @Test
    void test_StatsCollector_sum() throws InterruptedException {
        try (StatsCollector collector = new StatsCollector(4)) {
            collector.push("sum", new double[] {1, 2, 56, 0, 12});
            TimeUnit.SECONDS.sleep(1);
            assertThat(collector.stats().get("sum"))
                .isEqualTo(71);
        }
    }

    @Test
    void test_StatsCollector_average() throws InterruptedException {
        try (StatsCollector collector = new StatsCollector(4)) {
            collector.push("average", new double[] {5, 5, 5, 5, 5});
            collector.push("average", new double[] {1, 1, 1, 1, 1});
            TimeUnit.SECONDS.sleep(1);
            assertThat(collector.stats().get("average"))
                .isEqualTo(3);
        }
    }
}
