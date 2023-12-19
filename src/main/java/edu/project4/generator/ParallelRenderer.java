package edu.project4.generator;

import edu.project4.domain.BoundingRectangle;
import edu.project4.domain.Pixel;
import edu.project4.domain.Point;
import edu.project4.transformations.Transformation;
import java.awt.Color;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

public class ParallelRenderer extends Renderer {
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
            for (int thread = 0; thread < nThreads; thread++) {
                executorService.execute(() -> this.renderSamples(
                    result,
                    samples / nThreads,
                    iterPerSample,
                    afines,
                    variations,
                    rectangle,
                    symetri
                ));
            }
        }
        return result;
    }

    protected void plot(FractalImage canvas, BoundingRectangle rectangle, Point point, Color affinColor) {
        int x = canvas.width() - (int) Math.floor(((maxX - point.x()) / rectangle.width()) * canvas.width());
        int y = canvas.height() - (int) Math.floor(((maxY - point.y()) / rectangle.height()) * canvas.height());
        if (canvas.contains(x, y)) {
            lock.readLock().lock();
            Pixel pixel = canvas.pixel(x, y);
            lock.readLock().unlock();
            pixel = updatePixel(pixel, affinColor);
            lock.writeLock().lock();
            canvas.updatePixel(x, y, pixel);
            lock.writeLock().unlock();
        }
    }

}
