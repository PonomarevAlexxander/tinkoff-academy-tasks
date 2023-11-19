package edu.project3;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Objects;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class FilesLogsProviderTest {
    private static final Path TEST_FILE = Path.of("logs.txt");
    @BeforeAll
    static void createFile() throws IOException {
        Files.createFile(TEST_FILE);
        Files.write(TEST_FILE, List.of(
            "93.180.71.3 - - [17/May/2015:08:05:32 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"",
            "93.180.71.3 - - [17/May/2015:08:05:23 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.21)\"",
            "80.91.33.133 - - [17/May/2015:08:05:24 +0000] \"GET /downloads/product_1 HTTP/1.1\" 304 0 \"-\" \"Debian APT-HTTP/1.3 (0.8.16~exp12ubuntu10.17)\"",
            "217.168.17.5 - - [17/May/2015:08:05:34 +0000] \"GET /downloads/product_1 HTTP/1.1\" 200 490 \"-\" \"Debian APT-HTTP/1.3 (0.8.10.3)\""
        ));
    }

    @AfterAll
    static void removeFile() throws IOException {
        Files.delete(TEST_FILE);
    }
    @Test
    void test_FilesLogProvider() {
        LogParser parser = new NginxLogParser();
        LogsProvider provider = new FilesLogsProvider(List.of(TEST_FILE), parser);
        assertThat(provider.getLogs().stream()
            .filter(Objects::nonNull)
            .toList().size())
            .isEqualTo(4);
    }
}
