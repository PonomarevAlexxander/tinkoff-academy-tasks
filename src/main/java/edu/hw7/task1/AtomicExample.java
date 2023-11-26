package edu.hw7.task1;

import java.util.List;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Stream;

public class AtomicExample {
    private static final AtomicLong COUNTER = new AtomicLong();

    private AtomicExample() {
    }

    public static long incrementBy(long num) {
        List<Thread> threads = Stream.generate(() -> new Thread(COUNTER::incrementAndGet))
            .limit(num)
            .toList();
        threads.forEach(Thread::start);
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        return COUNTER.get();
    }

    public static AtomicLong getCounter() {
        return COUNTER;
    }
}
