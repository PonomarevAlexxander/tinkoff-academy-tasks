package edu.hw2.task3;

import java.util.Random;

public class FaultyConnection implements Connection {
    private static final double PROBABILITY = 0.5;

    private final Random random;

    public FaultyConnection(Random random) {
        this.random = random;
    }

    @Override
    public void execute(String command) throws ConnectionException {
        if (random.nextDouble() < PROBABILITY) {
            throw new ConnectionException("Command execution failed");
        }
    }

    @Override
    public void close() {
    }
}
