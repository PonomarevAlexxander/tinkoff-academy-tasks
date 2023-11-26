package edu.hw7.task4;

import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.function.Supplier;
import java.util.stream.Stream;

public class ParallelPiCalculator implements PiCalculator {
    private final long numberOfIterations;
    private static final int THREADS_NUMBER = 4;
    private static final int RADIUS = 100;

    public ParallelPiCalculator(long numberOfIterations) {
        this.numberOfIterations = numberOfIterations;
    }

    @Override
    @SuppressWarnings("MagicNumber")
    public double getPi() {
        Point lowerLeft = new Point(-RADIUS, -RADIUS);
        Point upperRight = new Point(RADIUS, RADIUS);
        try (ExecutorService service = Executors.newFixedThreadPool(THREADS_NUMBER)) {
            Supplier<Long> cycle = () -> {
                long pointsInCircle = 0;
                for (long iteration = 0; iteration < numberOfIterations / THREADS_NUMBER; iteration++) {
                    Point randomPoint = getRandomPointInRectangle(lowerLeft, upperRight);
                    if (isInCircle(randomPoint)) {
                        pointsInCircle++;
                    }
                }
                return pointsInCircle;
            };
            List<CompletableFuture<Long>> futures = Stream.generate(() -> CompletableFuture.supplyAsync(cycle, service))
                .limit(THREADS_NUMBER)
                .toList();
            long pointsInCircle = futures.stream()
                .mapToLong(longCompletableFuture -> {
                    try {
                        return longCompletableFuture.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                })
                .sum();
            return 4 * ((double) pointsInCircle / numberOfIterations);
        }
    }

    private boolean isInCircle(Point point) {
        return Math.pow(point.x(), 2) + Math.pow(point.y(), 2) <= Math.pow(RADIUS, 2);
    }

    private Point getRandomPointInRectangle(Point lowerLeftPoint, Point upperRightPoint) {
        var currentRandom = ThreadLocalRandom.current();
        return new Point(
            currentRandom.nextDouble(lowerLeftPoint.x(), upperRightPoint.x()),
            currentRandom.nextDouble(lowerLeftPoint.y(), upperRightPoint.y())
        );
    }
}
