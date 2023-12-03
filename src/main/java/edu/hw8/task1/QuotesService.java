package edu.hw8.task1;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class QuotesService {
    private final Map<String, String> quotesByTheme;

    public QuotesService(Map<String, String> quotesByTheme) {
        this.quotesByTheme = new ConcurrentHashMap<>(quotesByTheme);
    }

    String findQuoteWith(String word) {
        return quotesByTheme.get(word);
    }
}
