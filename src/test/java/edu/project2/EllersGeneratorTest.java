package edu.project2;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class EllersGeneratorTest {
    @ParameterizedTest
    @CsvSource({
        "-1, 0",
        "5, 1",
        "1, 2"
    })
    void test_generate_on_invalid_data(int height, int width) {
        EllersGenerator generator = new EllersGenerator();
        assertThatThrownBy(() -> generator.generate(height, width))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @CsvSource({
        "2, 2",
        "5, 100",
        "2, 2"
    })
    void test_generate_on_valid_data(int height, int width) {
        EllersGenerator generator = new EllersGenerator();
        assertThat(generator.generate(height, width))
            .isInstanceOf(Maze.class);
    }
}
