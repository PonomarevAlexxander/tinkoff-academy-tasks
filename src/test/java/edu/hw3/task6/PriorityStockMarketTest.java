package edu.hw3.task6;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.NullSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.assertj.core.api.AssertionsForClassTypes.assertThatThrownBy;

class PriorityStockMarketTest {

    @Test
    void test_PriorityStockMarket_with_normal_data() {
        PriorityStockMarket market = new PriorityStockMarket();
        market.add(new Stock(20));
        market.add(new Stock(2));
        market.add(new Stock(1000));
        market.add(new Stock(4));
        assertThat(market.mostValuableStock().price())
            .isEqualTo(1000);
        market.remove(new Stock(1000));
        assertThat(market.mostValuableStock().price())
            .isEqualTo(20);
    }

    @ParameterizedTest
    @NullSource
    void test_PriorityStockMarket_add_with_null_data(Stock stock) {
        PriorityStockMarket market = new PriorityStockMarket();
        assertThatThrownBy(() -> market.add(stock))
            .isInstanceOf(NullPointerException.class);
    }

    @ParameterizedTest
    @NullSource
    void test_PriorityStockMarket_remove_with_null_data(Stock stock) {
        PriorityStockMarket market = new PriorityStockMarket();
        assertThatThrownBy(() -> market.remove(stock))
            .isInstanceOf(NullPointerException.class);
    }

    @Test
    void test_PriorityStockMarket_mostValuableStock_with_no_data() {
        PriorityStockMarket market = new PriorityStockMarket();
        assertThat(market.mostValuableStock())
            .isNull();
    }

}
