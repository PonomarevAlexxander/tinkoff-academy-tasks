package edu.project4.domain;

public record BoundingRectangle(double x, double y, double width, double height) {
    public boolean contains(Point point) {
        return (point.x() <= (x + width) && point.x() >= x && point.y() <= y && point.y() >= (y - height));
    }
}
