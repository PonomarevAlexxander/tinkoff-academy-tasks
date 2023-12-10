package edu.hw9.task1;

import org.junit.jupiter.api.Test;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class StatsCollectorTest {
    @Test
    void test_StatsCollector_multithread() {
        StatsCollector collector = new StatsCollector();
        try (ExecutorService service = Executors.newFixedThreadPool(4)) {
            List<CompletableFuture<Void>> producers = Stream.generate(() -> CompletableFuture.runAsync(() -> {
                    for (int i = 0; i < 20; i++) {
                        collector.push("sum", new double[] {11, 11, 11, 11, 11});
                    }
                }))
                .limit(2)
                .toList();
            while (collector.isEmpty()) {
            }
            List<CompletableFuture<Double>> consumers = Stream.generate(() -> CompletableFuture.supplyAsync(() -> {
                    double sum = 0;
                    while (!collector.isEmpty()) {
                        double[] data = collector.whaitStats().data();
                        sum += Arrays.stream(data).sum();
                    }
                    return sum;
                })).limit(2)
                .toList();

            assertThat(consumers.stream()
                .mapToDouble(doubleCompletableFuture -> {
                    try {
                        return doubleCompletableFuture.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                })
                .sum())
                .isEqualTo(11 * 5 * 20 * 2);
        }
    }
}
