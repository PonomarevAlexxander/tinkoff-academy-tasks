package edu.hw8.task2;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;

public class FixedThreadPool implements ThreadPool {
    private final Thread[] threads;
    private final LinkedBlockingQueue<Runnable> tasks = new LinkedBlockingQueue<>();
    private volatile boolean started = false;
    private final int timeout = 100;

    public FixedThreadPool(int nThreads) {
        this.threads = new Thread[nThreads];
    }

    @Override
    public void start() {
        started = true;
        for (int index = 0; index < threads.length; index++) {
            threads[index] = new Thread(this::fetchTasks);
            threads[index].start();
        }
    }

    @Override
    public void execute(Runnable runnable) {
        try {
            if (!tasks.offer(runnable, 1, TimeUnit.SECONDS)) {
                throw new IllegalStateException("Task queue is full.");
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void close() {
        started = false;
        for (var thread : threads) {
            try {
                thread.join(timeout);
            } catch (InterruptedException e) {
                thread.interrupt();
            }
        }
    }

    private void fetchTasks() {
        while (!tasks.isEmpty() || started) {
            try {
                Runnable task = tasks.take();
                task.run();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }
}
