package edu.hw2.task3;

public class FaultyConnection implements Connection {
    private static final double PROBABILITY = 0.5;

    @Override
    public void execute(String command) throws ConnectionException {
        if (Math.random() < PROBABILITY) {
            throw new ConnectionException("Command execution failed");
        }
    }

    @Override
    public void close() {
    }
}
