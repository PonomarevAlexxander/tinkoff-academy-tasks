package edu.project4;

import edu.project4.generator.FractalImage;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import javax.imageio.ImageIO;

public final class ImageUtils {
    public enum ImageFormat {
        JPEG("jpg"),
        BMP("bmp"),
        PNG("png");

        private final String extension;

        ImageFormat(String extension) {
            this.extension = extension;
        }
    }

    private ImageUtils() {
    }

    public static void save(FractalImage image, Path file, ImageFormat format) throws IOException {
        BufferedImage bufferedImage = new BufferedImage(image.width(), image.height(), BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < image.width(); x++) {
            for (int y = 0; y < image.height(); y++) {
                int rgb = image.pixel(x, y).color().getRGB();
                bufferedImage.setRGB(x, y, rgb);
            }
        }
        File output = file.toAbsolutePath().toFile();
        ImageIO.write(bufferedImage, format.extension, output);
    }
}
