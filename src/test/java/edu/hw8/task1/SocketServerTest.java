package edu.hw8.task1;

import org.junit.jupiter.api.Test;
import java.io.IOException;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Stream;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class SocketServerTest {
    private static final Map<String, String> quotes = Map.of(
        "личности", "Не переходи на личности там, где их нет",
        "оскорбления", "Если твои противники перешли на личные оскорбления, будь уверена — твоя победа не за горами",
        "глупый", "А я тебе говорил, что ты глупый? Так вот, я забираю свои слова обратно... Ты просто бог идиотизма.",
        "интеллект", "Чем ниже интеллект, тем громче оскорбления"
    );
    private static final QuotesService quotesService = new QuotesService(quotes);

    @Test
    void test_SocketServer_on_one_client() throws Exception {
        try (SocketServer server = new SocketServer(quotesService)) {
            CountDownLatch latch = new CountDownLatch(1);
            new Thread(() -> {
                try {
                    latch.countDown();
                    server.start(6666, 4);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();
            latch.await(5, TimeUnit.SECONDS);

            SocketClient client = new SocketClient("localhost", 6666);
            assertThat(client.getQuoteByTheme("глупый"))
                .isEqualTo(quotes.get("глупый"));
        }
    }

    @Test
    void test_SocketServer_on_multiple_clients() {
        try (SocketServer server = new SocketServer(quotesService)) {
            CountDownLatch latch = new CountDownLatch(1);
            new Thread(() -> {
                try {
                    latch.countDown();
                    server.start(6666, 4);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }).start();
            latch.await(5, TimeUnit.SECONDS);

            try (ExecutorService clients = Executors.newFixedThreadPool(10)) {
                Stream.generate(() -> CompletableFuture
                    .supplyAsync(() -> {
                        SocketClient client = new SocketClient("localhost", 6666);
                        return client.getQuoteByTheme("глупый");
                    }, clients)
                    .thenAcceptAsync((answer) -> {
                        assertThat(answer)
                            .isEqualTo(quotes.get("глупый"));
                    }))
                    .limit(10)
                    .forEach(feature -> {
                        try {
                            feature.get();
                        } catch (InterruptedException | ExecutionException e) {
                            throw new RuntimeException(e);
                        }
                    });
            }
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
