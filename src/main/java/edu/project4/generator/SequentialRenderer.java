package edu.project4.generator;

import edu.project4.domain.BoundingRectangle;
import edu.project4.domain.Pixel;
import edu.project4.domain.Point;
import edu.project4.transformations.Transformation;
import java.awt.Color;
import java.util.List;
import java.util.Random;

public class SequentialRenderer implements Renderer {
    private final double minX = -1.5;
    private final double maxX = 1.5;
    private final double minY = -1.5;
    private final double maxY = 1.5;

    @Override
    @SuppressWarnings("MagicNumber")
    public FractalImage render(
        FractalImage canvas,
        List<Transformation> afines,
        List<Transformation> variations,
        int samples,
        short iterPerSample,
        boolean symetri
    ) {
        FractalImage result = FractalImage.create(canvas.width(), canvas.height());
        Random random = new Random();
        BoundingRectangle rectangle = new BoundingRectangle(minX, maxY, maxX - minX, maxY - minY);
        int wormupIterations = 20;
        for (int sample = 0; sample < samples; sample++) {
            Point point = new Point(random.nextDouble(minX, maxX), random.nextDouble(minY, maxY));
            for (int step = -wormupIterations; step < iterPerSample; step++) {
                Transformation affin = afines.get(random.nextInt(afines.size()));
                point = affin.apply(point);
                Transformation variation = variations.get(random.nextInt(variations.size()));
                point = variation.apply(point);
                if (symetri && step % 2 == 0) {
                    point = new Point(-point.x(), point.y());
                }
                if (step >= 0 && rectangle.contains(point)) {
                    plot(result, rectangle, point, affin.getColor());
                }
            }
        }
        return result;
    }

    private void plot(FractalImage canvas, BoundingRectangle rectangle, Point point, Color affinColor) {
        int x = canvas.width() - (int) Math.floor(((maxX - point.x()) / rectangle.width()) * canvas.width());
        int y = canvas.height() - (int) Math.floor(((maxY - point.y()) / rectangle.height()) * canvas.height());
        if (canvas.contains(x, y)) {
            Pixel pixel = canvas.pixel(x, y);
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
            canvas.updatePixel(x, y, pixel);
        }
    }
}
