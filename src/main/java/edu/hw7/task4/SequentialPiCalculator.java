package edu.hw7.task4;

import java.util.Random;

public class SequentialPiCalculator implements PiCalculator {
    private static final Random RANDOM = new Random();
    private static final int RADIUS = 100;
    private final long numberOfIterations;

    public SequentialPiCalculator(long numberOfIterations) {
        this.numberOfIterations = numberOfIterations;
    }

    @Override
    @SuppressWarnings("MagicNumber")
    public double getPi() {
        Point lowerLeft = new Point(-RADIUS, -RADIUS);
        Point upperRight = new Point(RADIUS, RADIUS);
        long pointsInCircle = 0;
        for (long iteration = 0; iteration < numberOfIterations; iteration++) {
            Point randomPoint = getRandomPointInRectangle(lowerLeft, upperRight);
            if (isInCircle(randomPoint)) {
                pointsInCircle++;
            }
        }
        return 4 * ((double) pointsInCircle / numberOfIterations);
    }

    private boolean isInCircle(Point point) {
        return Math.pow(point.x(), 2) + Math.pow(point.y(), 2) <= Math.pow(RADIUS, 2);
    }

    private Point getRandomPointInRectangle(Point lowerLeftPoint, Point upperRightPoint) {
        return new Point(
            RANDOM.nextDouble(lowerLeftPoint.x(), upperRightPoint.x()),
            RANDOM.nextDouble(lowerLeftPoint.y(), upperRightPoint.y())
        );
    }
}
