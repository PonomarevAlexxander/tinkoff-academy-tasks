package edu.hw2.task3;

import java.util.Random;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

class PopularCommandExecutorTest {
    @Test
    @DisplayName("Test PopularCommandExecutor with FaultyConnectionManager that throws Exception")
    void test_PopularCommandExecutor_with_FaultyConnectionManager_Exception() {
        PopularCommandExecutor executor = new PopularCommandExecutor(new FaultyConnectionManager(new Random() {
            @Override
            public double nextDouble() {
                return 0.1;
            }
        }), 5);
        Throwable error = catchThrowable(executor::updatePackages);
        assertThat(error)
            .hasCauseInstanceOf(ConnectionException.class)
            .isInstanceOf(ConnectionException.class);
    }

    @Test
    @DisplayName("Test PopularCommandExecutor with FaultyConnectionManager that throws no exception")
    void test_PopularCommandExecutor_with_FaultyConnectionManager_no_Exception() {
        PopularCommandExecutor executor = new PopularCommandExecutor(new FaultyConnectionManager(new Random() {
            @Override
            public double nextDouble() {
                return 0.9;
            }
        }), 5);
        assertThatNoException()
            .isThrownBy(executor::updatePackages);
    }

    @Test
    @DisplayName("Test PopularCommandExecutor with DefaultConnectionManager that throws exception")
    void test_PopularCommandExecutor_with_DefaultConnectionManager_that_throws_Exception() {
        PopularCommandExecutor executor = new PopularCommandExecutor(new DefaultConnectionManager(new Random() {
            @Override
            public double nextDouble() {
                return 0.1;
            }
        }), 5);
        Throwable error = catchThrowable(executor::updatePackages);
        assertThat(error)
            .hasCauseInstanceOf(ConnectionException.class)
            .isInstanceOf(ConnectionException.class);
    }

    @Test
    @DisplayName("Test PopularCommandExecutor with DefaultConnectionManager that throws no exception")
    void test_PopularCommandExecutor_with_DefaultConnectionManager_with_no_Exception() {
        PopularCommandExecutor executor = new PopularCommandExecutor(new DefaultConnectionManager(new Random() {
            @Override
            public double nextDouble() {
                return 0.9;
            }
        }), 5);
        assertThatNoException()
            .isThrownBy(executor::updatePackages);
    }
}
