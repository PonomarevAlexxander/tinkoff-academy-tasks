package edu.hw6.task4;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class FileGeneratorTest {
    private static final String FILENAME = "test.txt";
    private static final String DATA = "Programming is learned by writing programs. ― Brian Kernighan";

    @Test
    void test_FileGenerator() throws IOException {
        Path file = Path.of(FILENAME);
        FileGenerator.generateFile(file, DATA);
        assertThat(Files.readAllLines(file))
            .isEqualTo(List.of(
                DATA
            ));
        Files.delete(file);
    }

}
