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
                Pixel pixel = image.pixel(x, y);
                int hitCount = pixel.getHitCount();
                if (hitCount != 0) {
                    double normal = Math.log10(hitCount);
                    pixel.setNormal(normal);
                    if (normal > max) {
                        max = normal;
                    }
                }
            }
        }

        for (int y = 0; y < image.height(); y++) {
            for (int x = 0; x < image.width(); x++) {
                Pixel pixel = image.pixel(x, y);
                if (pixel.getHitCount() != 0) {
                    double normal = pixel.getNormal() / max;
                    Color pixelColor = pixel.getColor();
                    double pow = Math.pow(normal, 1.0 / gamma);
                    int red = (int) (pixelColor.getRed() * pow);
                    int green = (int) (pixelColor.getGreen() * pow);
                    int blue = (int) (pixelColor.getBlue() * pow);
                    pixel.setColor(new Color(red, green, blue));
                    pixel.setNormal(normal);
                }
            }
        }
    }
}
