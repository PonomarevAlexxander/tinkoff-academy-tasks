package edu.project4.domain;

import java.awt.Color;

public class Pixel {

    private Color color;
    private int hitCount;
    private double normal;

    public Pixel(Color color, int hitCount, double normal) {
        this.color = color;
        this.hitCount = hitCount;
        this.normal = normal;
    }

    public Color getColor() {
        return color;
    }

    public synchronized void setColor(Color color) {
        this.color = color;
    }

    public int getHitCount() {
        return hitCount;
    }

    public synchronized void setHitCount(int hitCount) {
        this.hitCount = hitCount;
    }

    public synchronized void increaseHitCount(int number) {
        this.hitCount += number;
    }

    public double getNormal() {
        return normal;
    }

    public synchronized void setNormal(double normal) {
        this.normal = normal;
    }
}
