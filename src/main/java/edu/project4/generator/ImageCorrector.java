package edu.project4.generator;

import edu.project4.domain.Pixel;
import java.awt.Color;

public class ImageCorrector implements ImageProcessor {
    private final double gamma;

    public ImageCorrector(double gamma) {
        this.gamma = gamma;
    }

    @Override
    public void process(FractalImage image) {
        double max = 0;
        for (int y = 0; y < image.height(); y++) {
            for (int x = 0; x < image.width(); x++) {
                if (image.pixel(x, y).hitCount() != 0) {
                    double normal = Math.log10(image.pixel(x, y).hitCount());
                    image.updatePixel(x, y, new Pixel(image.pixel(x, y).color(), image.pixel(x, y).hitCount(), normal));
                    if (normal > max) {
                        max = normal;
                    }
                }
            }
        }

        for (int y = 0; y < image.height(); y++) {
            for (int x = 0; x < image.width(); x++) {
                if (image.pixel(x, y).hitCount() != 0) {
                    double normal = image.pixel(x, y).normal() / max;
                    Color pixelColor = image.pixel(x, y).color();
                    int red = (int) (pixelColor.getRed() * Math.pow(normal, 1.0 / gamma));
                    int green = (int) (pixelColor.getGreen() * Math.pow(normal, 1.0 / gamma));
                    int blue = (int) (pixelColor.getBlue() * Math.pow(normal, 1.0 / gamma));
                    image.updatePixel(
                        x,
                        y,
                        new Pixel(new Color(red, green, blue), image.pixel(x, y).hitCount(), normal)
                    );
                }
            }
        }
    }
}
