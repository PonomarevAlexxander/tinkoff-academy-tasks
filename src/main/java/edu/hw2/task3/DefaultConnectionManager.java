package edu.hw2.task3;

import java.util.Random;

public class DefaultConnectionManager implements ConnectionManager {
    private final static double PROBABILITY = 0.2;
    private final Random random;

    public DefaultConnectionManager(Random random) {
        this.random = random;
    }

    @Override
    public Connection getConnection() {
        if (random.nextDouble() < PROBABILITY) {
            return new FaultyConnection(random);
        }
        return new StableConnection();
    }
}
