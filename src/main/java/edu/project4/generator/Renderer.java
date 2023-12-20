package edu.project4.generator;

import edu.project4.domain.BoundingRectangle;
import edu.project4.domain.Pixel;
import edu.project4.domain.Point;
import edu.project4.transformations.Transformation;
import java.awt.Color;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

public abstract class Renderer {
    @SuppressWarnings("MagicNumber")
    protected int wormupIterations = 20;

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
                    int x = result.width() - (int) Math.floor(
                        ((rectangle.x() + rectangle.width() - point.x()) / rectangle.width()) * result.width());
                    int y = result.height()
                        - (int) Math.floor(((rectangle.y() - point.y()) / rectangle.height()) * result.height());
                    if (result.contains(x, y)) {
                        Pixel pixel = result.pixel(x, y);
                        updatePixelColor(pixel, affin.getColor());
                    }
                }
            }
        }
    }

    protected void updatePixelColor(Pixel pixel, Color affinColor) {
        if (pixel.getHitCount() == 0) {
            pixel.setColor(affinColor);
            pixel.increaseHitCount(1);
        }
        Color pixelColor = pixel.getColor();
        int red = (pixelColor.getRed() + affinColor.getRed()) / 2;
        int green = (pixelColor.getGreen() + affinColor.getGreen()) / 2;
        int blue = (pixelColor.getBlue() + affinColor.getBlue()) / 2;
        Color newColor = new Color(red, green, blue);
        pixel.setColor(newColor);
        pixel.increaseHitCount(1);
    }
}
