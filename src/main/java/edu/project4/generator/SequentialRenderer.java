package edu.project4.generator;

import edu.project4.domain.BoundingRectangle;
import edu.project4.domain.Pixel;
import edu.project4.domain.Point;
import edu.project4.transformations.Transformation;
import java.awt.Color;
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

    protected void plot(FractalImage canvas, BoundingRectangle rectangle, Point point, Color affinColor) {
        int x = canvas.width() - (int) Math.floor(((maxX - point.x()) / rectangle.width()) * canvas.width());
        int y = canvas.height() - (int) Math.floor(((maxY - point.y()) / rectangle.height()) * canvas.height());
        if (canvas.contains(x, y)) {
            Pixel pixel = canvas.pixel(x, y);
            pixel = updatePixel(pixel, affinColor);
            canvas.updatePixel(x, y, pixel);
        }
    }
}
