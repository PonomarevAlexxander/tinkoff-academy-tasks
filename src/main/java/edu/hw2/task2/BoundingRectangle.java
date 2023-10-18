package edu.hw2.task2;

public abstract class BoundingRectangle {
    private final double height;
    private final double width;

    public BoundingRectangle(double height, double width) {
        if (height <= 0 || width <= 0) {
            throw new IllegalArgumentException("Height and width shall be above zero");
        }
        this.height = height;
        this.width = width;
    }

    public double getHeight() {
        return height;
    }

    public double getWidth() {
        return width;
    }

    public double getArea() {
        return height * width;
    }
}
