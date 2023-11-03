package edu.hw3.task6;

import java.util.Comparator;
import java.util.Objects;
import java.util.PriorityQueue;
import java.util.Queue;

public class PriorityStockMarket implements StockMarket {

    private final Queue<Stock> stocks = new PriorityQueue<>(Comparator.reverseOrder());

    @Override
    public void add(Stock stock) {
        Objects.requireNonNull(stock);
        stocks.add(stock);
    }

    @Override
    public void remove(Stock stock) {
        Objects.requireNonNull(stock);
        stocks.remove(stock);
    }

    @Override
    public Stock mostValuableStock() {
        return stocks.peek();
    }
}
