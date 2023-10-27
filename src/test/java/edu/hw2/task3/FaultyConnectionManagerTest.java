package edu.hw2.task3;

import java.util.Random;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class FaultyConnectionManagerTest {

    @Test
    void test_getConnection() {
        FaultyConnectionManager manager = new FaultyConnectionManager(new Random());
        Connection connection = manager.getConnection();
        assertThat(connection)
            .isInstanceOf(FaultyConnection.class);
    }
}
