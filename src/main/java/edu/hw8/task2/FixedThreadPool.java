package edu.hw8.task2;

import java.util.Queue;
import java.util.concurrent.LinkedBlockingQueue;

public class FixedThreadPool implements ThreadPool {
    private final Thread[] threads;
    private final Queue<Runnable> tasks = new LinkedBlockingQueue<>();

    public FixedThreadPool(int nThreads) {
        this.threads = new Thread[nThreads];
    }

    @Override
    public void start() {
        while (!tasks.isEmpty()) {
            for (int index = 0; index < threads.length; index++) {
                if (threads[index] == null || !threads[index].isAlive()) {
                    threads[index] = new Thread(tasks.poll());
                    threads[index].start();
                    break;
                }
            }
        }
    }

    @Override
    public void execute(Runnable runnable) {
        tasks.add(runnable);
    }

    @Override
    public void close() throws Exception {
        for (var thread : threads) {
            if (thread != null) {
                thread.join();
            }
        }
    }
}
