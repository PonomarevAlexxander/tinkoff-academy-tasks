package edu.hw3.task6;

import org.jetbrains.annotations.NotNull;

public record Stock(String ticker, int price) implements Comparable<Stock> {
    public Stock {
        if (price <= 0) {
            throw new IllegalArgumentException("stock price must be positive number");
        }
        if (ticker == null || ticker.isBlank()) {
            throw new IllegalArgumentException("ticker must be not blank string");
        }
    }

    @Override
    public int compareTo(@NotNull Stock o) {
        return Integer.compare(this.price, o.price);
    }
}
