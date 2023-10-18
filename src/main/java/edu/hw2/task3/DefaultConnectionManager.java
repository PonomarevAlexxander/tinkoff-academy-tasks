package edu.hw2.task3;

public class DefaultConnectionManager implements ConnectionManager {
    private static final double PROBABILITY = 0.2;

    @Override
    public Connection getConnection() {
        if (Math.random() < PROBABILITY) {
            return new FaultyConnection();
        }
        return new StableConnection();
    }
}
