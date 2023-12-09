package edu.project4.generator;

import edu.project4.domain.Pixel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import java.awt.Color;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class FractalImageTest {
    private final static FractalImage image = FractalImage.create(10, 10);

    @ParameterizedTest
    @CsvSource({
        "10, 10",
        "4, 45"
    })
    void test_create_on_normal_data(int width, int height) {
        assertThat(FractalImage.create(width, height).data().length)
            .isEqualTo(width * height);
    }

    @Test
    void test_create_on_invalid_data() {
        assertThatThrownBy(() -> FractalImage.create(-1, 0))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource({
        "1, 1, true",
        "0, 0, true",
        "-1, 5, false",
        "12, 1, false"
    })
    void test_contains(int x, int y, boolean expected) {
        assertThat(image.contains(x, y))
            .isEqualTo(expected);
    }

    @ParameterizedTest
    @CsvSource({
        "-10, 10",
        "0, 45"
    })
    void test_pixel_on_illegal_data(int x, int y) {
        assertThatThrownBy(() -> image.pixel(x, y))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource({
        "8, 8",
        "0, 4"
    })
    void test_updatePixel(int x, int y) {
        Pixel newPixel = new Pixel(Color.WHITE, 0, 0);
        image.updatePixel(x, y, newPixel);
        assertThat(image.pixel(x, y))
            .isEqualTo(newPixel);
    }

    @Test
    void test_data() {
        assertThat(image.data().length)
            .isEqualTo(100);
    }

    @Test
    void test_width() {
        assertThat(image.width())
            .isEqualTo(10);
    }

    @Test
    void test_height() {
        assertThat(image.height())
            .isEqualTo(10);
    }
}
