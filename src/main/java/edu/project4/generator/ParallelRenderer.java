package edu.project4.generator;

import edu.project4.domain.BoundingRectangle;
import edu.project4.transformations.Transformation;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ParallelRenderer extends Renderer {
    private final double minX = -1.5;
    private final double maxX = 1.5;
    private final double minY = -1.5;
    private final double maxY = 1.5;
    private final int nThreads;

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
}
