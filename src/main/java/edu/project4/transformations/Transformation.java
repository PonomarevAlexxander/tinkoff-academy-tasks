package edu.project4.transformations;

import edu.project4.domain.Point;
import java.awt.Color;
import java.util.function.Function;

public interface Transformation extends Function<Point, Point> {
    default Color getColor() {
        return Color.WHITE;
    }
}
