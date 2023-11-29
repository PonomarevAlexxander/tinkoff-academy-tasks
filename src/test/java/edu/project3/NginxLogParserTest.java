package edu.project3;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class NginxLogParserTest {

    @ParameterizedTest
    @ValueSource(strings = {
        "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"",
        "217.168.17.5 - - [17/May/2015:08:05:12 +0000] \"GET /downloads/product_2 HTTP/1.1\" 200 3316 \"-\" \"-\"",
        "74.125.60.158 - - [04/Jun/2015:04:06:15 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (1.0.1ubuntu2)\""
    })
    void test_parse_check_valid(String log) {
        LogParser parser = new NginxLogParser();
        assertThat(parser.parse(log))
            .isNotNull();
    }
}
