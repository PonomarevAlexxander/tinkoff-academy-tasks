package edu.hw7.task1;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class AtomicExample {
    private static final AtomicLong COUNTER = new AtomicLong(0);

    private AtomicExample() {
    }

    public static long incrementBy(int num) {
        try (ExecutorService executor = Executors.newFixedThreadPool(4)) {
            for (int times = 0; times < num; times++) {
                CompletableFuture.runAsync(COUNTER::incrementAndGet, executor).join();
            }
        }
        return COUNTER.get();
    }

    public static AtomicLong getCounter() {
        return COUNTER;
    }
}
