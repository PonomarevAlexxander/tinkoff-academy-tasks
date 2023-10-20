package edu.hw2.task3;

import java.util.Random;
import java.util.stream.Stream;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

class FaultyConnectionTest {
    private static final double FAULTY_PROBABILITY = 0.1;
    private static final double SUCCESS_PROBABILITY = 0.9;

    @Test
    @DisplayName("testFaultyConnection_execute")
    void testFaultyConnection_execute() {
        try (FaultyConnection connection = new FaultyConnection(new Random() {
            @Override
            public double nextDouble() {
                return FAULTY_PROBABILITY;
            }
        });) {
            Throwable exception = catchThrowable(() -> connection.execute(""));
            assertThat(exception)
                .isInstanceOf(ConnectionException.class);
        } catch (Exception ignored) {
        }
        try (FaultyConnection connection = new FaultyConnection(new Random() {
            @Override
            public double nextDouble() {
                return SUCCESS_PROBABILITY;
            }
        });) {
            Throwable exception = catchThrowable(() -> connection.execute(""));
            assertThat(exception)
                .isNull();
        } catch (Exception ignored) {
        }

    }

}
