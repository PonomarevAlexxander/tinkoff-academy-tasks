package edu.hw6.task3;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class FiltersTest {
    @Test
    void test_FiltersChains() throws IOException {
        Path testDir = Path.of("src", "test", "resources", "edu", "hw6", "task3");
        AbstractFilter filter = Filters.globMatches("*.png")
            .and(Filters.readable())
            .and(Filters.isLargerThan(1024 * 90))
            .and(Filters.fileSignaturesMatch((char) 0x89, 'P', 'N', 'G'))
            .and(Filters.nameMatches("test-.*"));
        Path result = null;
        Integer counter = 0;
        try (DirectoryStream<Path> stream = Files.newDirectoryStream(testDir, filter)) {
            for (var path : stream) {
                result = path;
                counter++;
            }
        }
        assertThat(counter)
            .isEqualTo(1);
        assertThat(result)
            .isEqualTo(Path.of(testDir.toString(), "test-godfather-matches.png"));
    }
}
