package edu.hw9.task1;

import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class StatsCollector {
    private final BlockingQueue<Metric> queue = new LinkedBlockingQueue<>();

    @SuppressWarnings("MagicNumber")
    public void push(String metricName, double[] data) {
        try {
            if (!queue.offer(new Metric(metricName, data), 5, TimeUnit.SECONDS)) {
                throw new IllegalStateException("collector is full of tasks");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("thread has been interrupted while trying to add metric", e);
        }
    }

    public Metric whaitStats() {
        try {
            return queue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException("thread has been interrupted while trying to get metric waiting", e);
        }
    }

    public boolean isEmpty() {
        return queue.isEmpty();
    }
}
