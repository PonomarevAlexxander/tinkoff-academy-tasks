package edu.hw2.task3;

public final class PopularCommandExecutor {
    private final ConnectionManager manager;
    private final int maxAttempts;

    public PopularCommandExecutor(ConnectionManager manager, int maxAttempts) {
        this.manager = manager;
        this.maxAttempts = maxAttempts;
    }

    public void updatePackages() {
        tryExecute("apt update && apt upgrade -y");
    }

    private void tryExecute(String command) throws ConnectionException {
        int attempts = 0;
        while (true) {
            try (Connection connection = manager.getConnection()) {
                connection.execute(command);
                return;
            } catch (Exception error) {
                if (attempts > maxAttempts) {
                    throw new ConnectionException(
                        "Error: " + command + " has failed to execute " + maxAttempts + " times", error);
                }
            }
            attempts++;
        }
    }
}
