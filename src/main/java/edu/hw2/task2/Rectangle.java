package edu.hw2.task2;

public class Rectangle {
    private final double height;
    private final double width;

    public Rectangle(double height, double width) {
        if (height <= 0 || width <= 0) {
            throw new IllegalArgumentException("Height and width shall be above zero");
        }
        this.height = height;
        this.width = width;
    }

    public Rectangle setHeight(double height) {
        return new Rectangle(height, this.width);
    }

    public Rectangle setWidth(double width) {
        return new Rectangle(this.height, width);
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
