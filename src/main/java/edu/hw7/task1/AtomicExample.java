package edu.hw7.task1;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;

public class AtomicExample {
    private static final AtomicLong COUNTER = new AtomicLong(0);
    private static final int THREADS_NUMBER = 4;

    private AtomicExample() {
    }

    public static long incrementBy(int num) {
        try (ExecutorService executor = Executors.newFixedThreadPool(THREADS_NUMBER)) {
            for (int times = 0; times < num; times++) {
                CompletableFuture.runAsync(COUNTER::incrementAndGet, executor);
            }
        }
        return COUNTER.get();
    }

    public static AtomicLong getCounter() {
        return COUNTER;
    }
}
