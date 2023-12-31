package edu.hw2.task3;

import java.util.Random;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

class FaultyConnectionTest {
    private static final double FAULTY_PROBABILITY = 0.1;
    private static final double SUCCESS_PROBABILITY = 0.9;

    @Test
    @DisplayName("test FaultyConnection.execute to fail")
    void test_FaultyConnection_execute_to_fail() {
        try (FaultyConnection connection = new FaultyConnection(new Random() {
            @Override
            public double nextDouble() {
                return FAULTY_PROBABILITY;
            }
        })) {
            Throwable exception = catchThrowable(() -> connection.execute(""));
            assertThat(exception)
                .isInstanceOf(ConnectionException.class);
        }
    }

    @Test
    @DisplayName("test FaultyConnection.execute to success")
    void test_FaultyConnection_execute_to_success() {
        try (FaultyConnection connection = new FaultyConnection(new Random() {
            @Override
            public double nextDouble() {
                return SUCCESS_PROBABILITY;
            }
        })) {
            Throwable exception = catchThrowable(() -> connection.execute(""));
            assertThat(exception)
                .isNull();
        }
    }

}
