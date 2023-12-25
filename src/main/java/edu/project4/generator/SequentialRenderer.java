package edu.project4.generator;

import edu.project4.domain.BoundingRectangle;
import edu.project4.transformations.Transformation;
import java.util.List;

public class SequentialRenderer extends Renderer {
    private final double minX = -1.5;
    private final double maxX = 1.5;
    private final double minY = -1.5;
    private final double maxY = 1.5;

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
        BoundingRectangle rectangle = new BoundingRectangle(minX, maxY, maxX - minX, maxY - minY);
        renderSamples(result, samples, iterPerSample, afines, variations, rectangle, symetri);
        return result;
    }
}
