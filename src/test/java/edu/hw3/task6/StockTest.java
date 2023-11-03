package edu.hw3.task6;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class StockTest {

    @ParameterizedTest
    @ValueSource(ints = {-30, 0})
    void test_Stock_with_unexpected_price_data(int price) {
        assertThatThrownBy(() -> new Stock("some", price))
            .isInstanceOf(IllegalArgumentException.class);
    }

    @ParameterizedTest
    @NullSource
    void test_Stock_with_unexpected_ticker_data(String ticker) {
        assertThatThrownBy(() -> new Stock(ticker, 1))
            .isInstanceOf(IllegalArgumentException.class);
    }
}
