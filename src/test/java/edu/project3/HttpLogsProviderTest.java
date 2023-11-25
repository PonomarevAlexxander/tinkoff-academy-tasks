package edu.project3;

import java.io.IOException;
import java.net.URI;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class HttpLogsProviderTest {
    private static final String TEST_ENDPOINT =
        "https://raw.githubusercontent.com/elastic/examples/master/Common%20Data%20Formats/nginx_logs/nginx_logs";

    @Test
    void test_HttpLogsProvider() throws IOException, InterruptedException {
        LogParser parser = new NginxLogParser();
        LogsProvider provider = new HttpLogsProvider(List.of(URI.create(TEST_ENDPOINT)), parser);
        assertThat(provider.getLogs().stream()
            .filter(Objects::nonNull)
            .toList().size())
            .isEqualTo(51462);
    }
}
