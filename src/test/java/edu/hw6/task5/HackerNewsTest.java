package edu.hw6.task5;

import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class HackerNewsTest {
    @Test
    void test_HackerNews() {
        long[] topStories = HackerNews.hackerNewsTopStories();
        assertThat(topStories)
            .isNotEmpty();
        assertThat(HackerNews.news(topStories[0]))
            .isNotNull();
    }
}
