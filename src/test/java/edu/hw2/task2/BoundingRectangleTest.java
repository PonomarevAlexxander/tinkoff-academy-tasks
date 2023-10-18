package edu.hw2.task2;

import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.CsvSource;
import org.junit.jupiter.params.provider.MethodSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class BoundingRectangleTest {
    @ParameterizedTest
    @DisplayName("Test BoundingRectangle getArea on valid data")
    @MethodSource("provideBoundingRectangles")
    void testBoundingRectangleArea(BoundingRectangle obj, double area) {
        assertThat(obj.getArea())
            .isEqualTo(area);
    }
    private static Stream<Arguments> provideBoundingRectangles() {
        return Stream.of(
            Arguments.of(new Rectangle(10, 20), 200),
            Arguments.of(new Square(20), 400),
            Arguments.of(new Rectangle(1, 200), 200)
        );
    }

    @ParameterizedTest
    @DisplayName("Test BoundingRectangle getArea on invalid data")
    @CsvSource({
        "-100, 400",
        "0, 200",
        "100, 0"
    })
    void testBoundingRectangleOnInvalidData(double height, double width) {
        assertThatThrownBy(() -> {
            BoundingRectangle obj = new Rectangle(height, width);
        })
            .isInstanceOf(IllegalArgumentException.class)
            .message()
            .isNotEmpty();
    }
}
