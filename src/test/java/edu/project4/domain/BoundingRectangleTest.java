package edu.project4.domain;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class BoundingRectangleTest {
    private static BoundingRectangle rectangle = new BoundingRectangle(0, 0, 10, 10);

    @ParameterizedTest
    @CsvSource({
        "1, -1, true",
        "0, 0, true",
        "-1, 5, false",
        "12, 1, false"
    })
    void test_contains(int x, int y, boolean expected) {
        assertThat(rectangle.contains(new Point(x, y)))
            .isEqualTo(expected);
    }
}
