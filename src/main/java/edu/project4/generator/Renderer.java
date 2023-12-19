package edu.project4.generator;

import edu.project4.domain.BoundingRectangle;
import edu.project4.domain.Pixel;
import edu.project4.domain.Point;
import edu.project4.transformations.Transformation;
import java.awt.Color;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Renderer {
    public abstract FractalImage render(
        FractalImage canvas,
        List<Transformation> afines,
        List<Transformation> variations,
        int samples,
        short iterPerSample,
        boolean symetri
    );

    protected void renderSamples(
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
                ThreadLocalRandom.current().nextDouble(rectangle.x(), rectangle.x() + rectangle.width()),
                ThreadLocalRandom.current().nextDouble(rectangle.y() - rectangle.height(), rectangle.y())
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

    protected abstract void plot(FractalImage canvas, BoundingRectangle rectangle, Point point, Color affinColor);

    protected Pixel updatePixel(Pixel pixel, Color affinColor) {
        if (pixel.hitCount() == 0) {
            return new Pixel(affinColor, 1, 0);
        }
        Color pixelColor = pixel.color();
        int red = (pixelColor.getRed() + affinColor.getRed()) / 2;
        int green = (pixelColor.getGreen() + affinColor.getGreen()) / 2;
        int blue = (pixelColor.getBlue() + affinColor.getBlue()) / 2;
        Color newColor = new Color(red, green, blue);
        return new Pixel(newColor, pixel.hitCount() + 1, 0);
    }
}
