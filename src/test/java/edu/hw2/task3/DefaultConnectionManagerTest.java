package edu.hw2.task3;

import java.util.Random;
import java.util.stream.Stream;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.junit.jupiter.api.Assertions.*;

class DefaultConnectionManagerTest {

    @ParameterizedTest
    @MethodSource("provideProbabilityForManager")
    void test_getConnection(double probability, Class<Connection> result) {
        DefaultConnectionManager manager = new DefaultConnectionManager(new Random() {
            @Override
            public double nextDouble() {
                return probability;
            }
        });
        Connection connection = manager.getConnection();
        assertThat(connection)
            .isInstanceOf(result);
    }

    private static Stream<Arguments> provideProbabilityForManager() {
        return Stream.of(
            Arguments.of(0.5, StableConnection.class),
            Arguments.of(0.1, FaultyConnection.class)
        );
    }
}
