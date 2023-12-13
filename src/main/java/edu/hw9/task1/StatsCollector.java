package edu.hw9.task1;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicLong;

public class StatsCollector implements AutoCloseable {
    private final BlockingQueue<Metric> taskQueue = new LinkedBlockingQueue<>();
    private final ConcurrentHashMap<String, Double> statistics = new ConcurrentHashMap<>();
    private final ExecutorService service;
    private final AtomicLong dataSize = new AtomicLong(0);

    public StatsCollector(int threads) {
        this.service = Executors.newFixedThreadPool(threads);
        for (int i = 0; i < threads; i++) {
            service.submit(this::processMetrics);
        }
    }

    @SuppressWarnings("MagicNumber")
    public void push(String metricName, double[] data) {
        try {
            if (!taskQueue.offer(new Metric(metricName, data), 5, TimeUnit.SECONDS)) {
                throw new IllegalStateException("collector is full of tasks");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException("thread has been interrupted while trying to add metric", e);
        }
    }

    public Map<String, Double> stats() {
        return new HashMap<>(statistics);
    }

    public boolean noTasks() {
        return taskQueue.isEmpty();
    }

    @Override
    public void close() {
        service.shutdown();
    }

    private void processMetrics() {
        while (!service.isShutdown()) {
            Metric metric = waitNext();
            switch (metric.name()) {
                case "sum" -> {
                    double sum = Arrays.stream(metric.data()).sum();
                    statistics.merge(metric.name(), sum, Double::sum);
                }
                case "average" -> {
                    double sum = Arrays.stream(metric.data()).sum();
                    int length = metric.data().length;
                    statistics.merge(
                        metric.name(),
                        sum / length,
                        (oldValue, value) -> (oldValue * dataSize.addAndGet(length) + value) / dataSize.get()
                    );
                }
                case "max" -> {
                    OptionalDouble max = Arrays.stream(metric.data()).max();
                    if (max.isEmpty()) {
                        continue;
                    }
                    statistics.merge(metric.name(), max.getAsDouble(), Double::max);
                }
                case "min" -> {
                    OptionalDouble min = Arrays.stream(metric.data()).min();
                    if (min.isEmpty()) {
                        continue;
                    }
                    statistics.merge(metric.name(), min.getAsDouble(), Double::max);
                }
                default -> {
                    throw new IllegalArgumentException("unsupported metric");
                }
            }
        }
    }

    private Metric waitNext() {
        try {
            return taskQueue.take();
        } catch (InterruptedException e) {
            throw new RuntimeException("thread has been interrupted while trying to get metric waiting", e);
        }
    }
}
