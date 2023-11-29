package edu.project3;

import java.time.OffsetDateTime;
import java.time.ZoneOffset;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class LogsAnalyzerTest {
    private static final List<LogRecord> LOGS = List.of(
        new LogRecord(
            "93.180.71.3",
            "-",
            OffsetDateTime.of(2011, 5, 17, 8, 5, 32, 0, ZoneOffset.ofHours(0)),
            "GET /downloads/product_1 HTTP/1.1",
            304,
            0,
            "-",
            "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
        ),
        new LogRecord(
            "123.180.123.3",
            "-",
            OffsetDateTime.of(2015, 2, 17, 10, 5, 32, 0, ZoneOffset.ofHours(0)),
            "GET /downloads/product_1 HTTP/1.1",
            200,
            100,
            "-",
            "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
        ),
        new LogRecord(
            "123.180.123.3",
            "-",
            OffsetDateTime.of(2015, 1, 1, 10, 23, 32, 0, ZoneOffset.ofHours(0)),
            "GET /downloads/product_2 HTTP/1.1",
            200,
            500,
            "-",
            "Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)"
        )
    );

    @Test
    void test_getBeginDate() {
        LogsAnalyzer analyzer = new LogsAnalyzer(LOGS);
        assertThat(analyzer.getBeginDate())
            .isEqualTo(OffsetDateTime.of(2011, 5, 17, 8, 5, 32, 0, ZoneOffset.ofHours(0)));
    }

    @Test
    void test_getEndDate() {
        LogsAnalyzer analyzer = new LogsAnalyzer(LOGS);
        assertThat(analyzer.getEndDate())
            .isEqualTo(OffsetDateTime.of(2015, 2, 17, 10, 5, 32, 0, ZoneOffset.ofHours(0)));
    }

    @Test
    void test_getRushHours() {
        LogsAnalyzer analyzer = new LogsAnalyzer(LOGS);
        assertThat(analyzer.getRushHours().get(10))
            .isEqualTo(2);
    }

    @Test
    void test_getMostFrequentIp() {
        LogsAnalyzer analyzer = new LogsAnalyzer(LOGS);
        assertThat(analyzer.getMostFrequentIp())
            .isEqualTo("123.180.123.3");
    }

    @Test
    void test_getTotalNumberOfRequests() {
        LogsAnalyzer analyzer = new LogsAnalyzer(LOGS);
        assertThat(analyzer.getTotalNumberOfRequests())
            .isEqualTo(3);
    }

    @Test
    void test_getMostFrequentResources() {
        LogsAnalyzer analyzer = new LogsAnalyzer(LOGS);
        assertThat(analyzer.getMostFrequentResources(1).containsKey("/downloads/product_1"))
            .isTrue();
    }

    @Test
    void test_getMostFrequentStatusCodes() {
        LogsAnalyzer analyzer = new LogsAnalyzer(LOGS);
        assertThat(analyzer.getMostFrequentStatusCodes(1).get(200))
            .isEqualTo(2);
    }

    @Test
    void test_getAverageResponseSize() {
        LogsAnalyzer analyzer = new LogsAnalyzer(LOGS);
        assertThat(analyzer.getAverageResponseSize())
            .isEqualTo(200);
    }
}
