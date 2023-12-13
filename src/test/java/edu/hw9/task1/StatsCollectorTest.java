package edu.hw9.task1;

import org.junit.jupiter.api.Test;
import java.util.Map;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
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
            while (!collector.noTasks()) {
            }
            Map<String, Double> stats = collector.stats();
            assertThat(stats.get("sum"))
                .isNotNull();
        }
    }
}
