package edu.project4.generator;

import edu.project4.domain.BoundingRectangle;
import edu.project4.domain.Pixel;
import edu.project4.domain.Point;
import edu.project4.transformations.Transformation;
import java.awt.Color;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadLocalRandom;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;
import java.util.stream.Stream;

public class ParallelRenderer implements Renderer {
    private final double minX = -1.5;
    private final double maxX = 1.5;
    private final double minY = -1.5;
    private final double maxY = 1.5;
    private final int nThreads;
    private ReadWriteLock lock = new ReentrantReadWriteLock();

    public ParallelRenderer(int nThreads) {
        this.nThreads = nThreads;
    }

    @Override
    public FractalImage render(
        FractalImage canvas,
        List<Transformation> afines,
        List<Transformation> variations,
        int samples,
        short iterPerSample,
        boolean symetri
    ) {
        FractalImage result = FractalImage.create(canvas.width(), canvas.height());
        try (ExecutorService executorService = Executors.newFixedThreadPool(nThreads)) {
            BoundingRectangle rectangle = new BoundingRectangle(minX, maxY, maxX - minX, maxY - minY);

            Stream.generate(() -> CompletableFuture.runAsync(() -> this.renderSamples(
                    result,
                    samples / nThreads,
                    iterPerSample,
                    afines,
                    variations,
                    rectangle,
                    symetri
                ), executorService))
                .limit(nThreads)
                .forEach(feature -> {
                    try {
                        feature.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException("failed to complete task", e);
                    }
                });
        }
        return result;
    }

    @SuppressWarnings("MagicNumber")
    private void renderSamples(
        FractalImage result,
        int samples,
        int iterPerSample,
        List<Transformation> afines,
        List<Transformation> variations,
        BoundingRectangle rectangle,
        boolean symetri
    ) {
        int wormupIterations = 20;
        for (int sample = 0; sample < samples; sample++) {
            Point point = new Point(
                ThreadLocalRandom.current().nextDouble(minX, maxX),
                ThreadLocalRandom.current().nextDouble(minY, maxY)
            );
            for (int step = -wormupIterations; step < iterPerSample; step++) {
                Transformation affin = afines.get(ThreadLocalRandom.current().nextInt(afines.size()));
                point = affin.apply(point);
                Transformation variation = variations.get(ThreadLocalRandom.current().nextInt(variations.size()));
                point = variation.apply(point);
                if (symetri && step % 2 == 0) {
                    point = new Point(-point.x(), point.y());
                }
                if (step >= 0 && rectangle.contains(point)) {
                    plot(result, rectangle, point, affin.getColor());
                }
            }
        }
    }

    private void plot(FractalImage canvas, BoundingRectangle rectangle, Point point, Color affinColor) {
        int x = canvas.width() - (int) Math.floor(((maxX - point.x()) / rectangle.width()) * canvas.width());
        int y = canvas.height() - (int) Math.floor(((maxY - point.y()) / rectangle.height()) * canvas.height());
        if (canvas.contains(x, y)) {
            lock.readLock().lock();
            Pixel pixel = canvas.pixel(x, y);
            lock.readLock().unlock();
            if (pixel.hitCount() == 0) {
                pixel = new Pixel(affinColor, 1, 0);
            } else {
                Color pixelColor = pixel.color();
                int red = (pixelColor.getRed() + affinColor.getRed()) / 2;
                int green = (pixelColor.getGreen() + affinColor.getGreen()) / 2;
                int blue = (pixelColor.getBlue() + affinColor.getBlue()) / 2;
                Color newColor = new Color(red, green, blue);
                pixel = new Pixel(newColor, pixel.hitCount() + 1, 0);
            }
            lock.writeLock().lock();
            canvas.updatePixel(x, y, pixel);
            lock.writeLock().unlock();
        }
    }

}
