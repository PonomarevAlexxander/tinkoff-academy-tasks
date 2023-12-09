package edu.project4.transformations;

import edu.project4.domain.Point;
import java.util.concurrent.ThreadLocalRandom;

public class NonLinearTransformations {
    private NonLinearTransformations() {
    }

    public static Transformation sinusoidal() {
        return point -> new Point(Math.sin(point.x()), Math.sin(point.y()));
    }

    public static Transformation spherical() {
        return point -> {
            double radius = getRadius(point.x(), point.y());
            return new Point(point.x() / radius, point.y() / radius);
        };
    }

    public static Transformation swirl() {
        return point -> {
            double radius2 = Math.pow(getRadius(point.x(), point.y()), 2);
            return new Point(
                point.x() * Math.sin(radius2) - point.y() * Math.cos(radius2),
                point.x() * Math.cos(radius2) - point.y() * Math.sin(radius2)
            );
        };
    }

    public static Transformation horseshoe() {
        return point -> {
            double radius = getRadius(point.x(), point.y());
            return new Point(
                (point.x() - point.y()) * (point.x() + point.y()) / radius,
                2 * point.x() * point.y() / radius
            );
        };
    }

    public static Transformation polar() {
        return point -> {
            double radius = getRadius(point.x(), point.y());
            return new Point(
                getTeta(point.x(), point.y()) / Math.PI,
                radius - 1
            );
        };
    }

    public static Transformation handkerchief() {
        return point -> {
            double radius = getRadius(point.x(), point.y());
            double teta = getTeta(point.x(), point.y());
            return new Point(
                radius * Math.sin(teta + radius),
                radius * Math.cos(teta - radius)
            );
        };
    }

    public static Transformation heart() {
        return point -> {
            double radius = getRadius(point.x(), point.y());
            double teta = getTeta(point.x(), point.y());
            return new Point(
                radius * Math.sin(teta * radius),
                -radius * Math.cos(teta * radius)
            );
        };
    }

    public static Transformation disk() {
        return point -> {
            double radius = getRadius(point.x(), point.y());
            double tetaPi = getTeta(point.x(), point.y()) / Math.PI;
            return new Point(
                tetaPi * Math.sin(Math.PI * radius),
                tetaPi * Math.cos(Math.PI * radius)
            );
        };
    }

    public static Transformation cross() {
        return point -> {
            double sqrt = Math.sqrt(1 / (Math.pow(Math.pow(point.x(), 2) - Math.pow(point.y(), 2), 2)));
            return new Point(
                sqrt * point.x(),
                sqrt * point.y()
            );
        };
    }

    public static Transformation tangent() {
        return point -> {
            return new Point(
                Math.sin(point.x()) / Math.cos(point.y()),
                Math.tan(point.y())
            );
        };
    }

    public static Transformation cylinder() {
        return point -> {
            return new Point(
                Math.sin(point.x()),
                point.y()
            );
        };
    }

    public static Transformation twintrian() {
        return point -> {
            double radius = getRadius(point.x(), point.y());
            double psi = ThreadLocalRandom.current().nextDouble();
            double t = Math.log10(Math.pow(Math.sin(psi * radius), 2)) + Math.cos(psi * radius);
            return new Point(
                point.x() * t,
                t - Math.PI * Math.sin(psi * radius)
            );
        };
    }

    private static double getRadius(double x, double y) {
        return Math.sqrt(Math.pow(x, 2) + Math.pow(y, 2));
    }

    private static double getTeta(double x, double y) {
        return Math.atan(x / y);
    }

    private static double getFi(double x, double y) {
        return Math.atan(y / x);
    }
}
