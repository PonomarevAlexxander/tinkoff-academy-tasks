package edu.hw3.task6;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class StockTest {

    @ParameterizedTest
    @ValueSource(ints = {-30, 0})
    void test_Stock_with_unexpected_data(int price) {
        assertThatThrownBy(() -> new Stock(price))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
