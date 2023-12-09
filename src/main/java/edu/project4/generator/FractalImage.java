package edu.project4.generator;

import edu.project4.domain.Pixel;
import java.awt.Color;

public record FractalImage(Pixel[] data, int width, int height) {
    public static FractalImage create(int width, int height) {
        if (width < 0 || height < 0) {
            throw new IllegalArgumentException("height and width shoul be positive");
        }
        FractalImage fractalImage = new FractalImage(new Pixel[width * height], width, height);
        for (int index = 0; index < width * height; index++) {
            fractalImage.data()[index] = new Pixel(Color.black, 0, 0);
        }
        return fractalImage;
    }

    public boolean contains(int x, int y) {
        return (x < width && y < height && x >= 0 && y >= 0);
    }

    public Pixel pixel(int x, int y) {
        if (x >= width || x < 0) {
            throw new IllegalArgumentException("'x' should be bound to image size");
        }
        if (y >= height || y < 0) {
            throw new IllegalArgumentException("'y' should be bound to image size");
        }
        return data[y * width + x];
    }

    public void updatePixel(int x, int y, Pixel pixel) {
        data[y * width + x] = pixel;
    }
}
