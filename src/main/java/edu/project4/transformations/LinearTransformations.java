package edu.project4.transformations;

import edu.project4.domain.Point;
import java.awt.Color;

public class LinearTransformations {
    private LinearTransformations() {
    }

    public static Transformation affin(double a, double b, double d, double e, double c, double f, Color color) {
        double ad = Math.pow(a, 2) + Math.pow(d, 2);
        if (ad > 1) {
            throw new IllegalArgumentException("a^2 + d^2 < 1");
        }
        double be = Math.pow(b, 2) + Math.pow(e, 2);
        if (be > 1) {
            throw new IllegalArgumentException("b^2 + e^2 < 1");
        }
        if (ad + be > 1 + Math.pow(a * e - b * d, 2)) {
            throw new IllegalArgumentException("a^2 + d^2 + b^2 + e^2 < 1 + (ae - bd)^2");
        }

        return new Transformation() {
            @Override
            public Color getColor() {
                return color;
            }

            @Override
            public Point apply(Point point) {
                return new Point(a * point.x() + b * point.y() + c, d * point.x() + e * point.y() + f);
            }
        };
    }
}
