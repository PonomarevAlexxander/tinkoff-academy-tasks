package edu.hw7.task1;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public class AtomicExample {
    private static final AtomicLong COUNTER = new AtomicLong();

    private AtomicExample() {
    }

    public static long incrementBy(int num) {
        try (ExecutorService executor = Executors.newFixedThreadPool(num)) {
            CompletableFuture.runAsync(COUNTER::incrementAndGet, executor);
        }
        return COUNTER.get();
    }

    public static AtomicLong getCounter() {
        return COUNTER;
    }
}
