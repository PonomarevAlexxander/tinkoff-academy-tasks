package edu.hw2.task3;

import java.util.Random;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;
import static org.junit.jupiter.api.Assertions.*;

class StableConnectionTest {

    @Test
    @DisplayName("test StableConnection")
    void test_StableConnection() {
        try (StableConnection connection = new StableConnection()) {
            Throwable exception = catchThrowable(() -> connection.execute(""));
            assertThat(exception)
                .isNull();
        } catch (Exception ignored) {
        }
    }
}
