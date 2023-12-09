package edu.project4;

import edu.project4.generator.FractalImage;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;
import java.io.IOException;
import java.nio.file.Path;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class ImageUtilsTest {

    @Test
    void test_save(@TempDir Path tempDir) throws IOException {
        Path image = tempDir.resolve("image.jpg");
        ImageUtils.save(FractalImage.create(100, 100), image, ImageUtils.ImageFormat.JPEG);
        assertThat(image.toFile().canRead())
            .isTrue();
    }
}
