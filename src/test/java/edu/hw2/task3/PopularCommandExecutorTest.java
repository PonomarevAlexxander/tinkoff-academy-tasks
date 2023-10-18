package edu.hw2.task3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatNoException;
import static org.assertj.core.api.AssertionsForClassTypes.catchThrowable;

class PopularCommandExecutorTest {
    @Test
    @DisplayName("Test PopularCommandExecutor with FaultyConnectionManager")
    void test_PopularCommandExecutor_with_FaultyConnectionManager() {
        PopularCommandExecutor executor = new PopularCommandExecutor(new FaultyConnectionManager(), 0);
        Throwable error = null;
        while (true) {
            error = catchThrowable(executor::updatePackages);
            if (error != null) {
                assertThat(error)
                    .hasCauseInstanceOf(ConnectionException.class)
                    .isInstanceOf(ConnectionException.class);
                break;
            }
        }
    }

    @Test
    @DisplayName("Test PopularCommandExecutor with FaultyConnectionManager")
    void test_PopularCommandExecutor_with_DefaultConnectionManager() {
        PopularCommandExecutor executor = new PopularCommandExecutor(new DefaultConnectionManager(), 100);
        assertThatNoException()
            .isThrownBy(executor::updatePackages);
    }
}
