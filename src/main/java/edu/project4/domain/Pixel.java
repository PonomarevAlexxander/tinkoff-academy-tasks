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

    public synchronized void updateColor(Color affinColor) {
        if (hitCount == 0) {
            color = affinColor;
            hitCount += 1;
            return;
        }
        Color pixelColor = color;
        int red = (pixelColor.getRed() + affinColor.getRed()) / 2;
        int green = (pixelColor.getGreen() + affinColor.getGreen()) / 2;
        int blue = (pixelColor.getBlue() + affinColor.getBlue()) / 2;
        color = new Color(red, green, blue);
        hitCount += 1;
    }
}
