package edu.hw6.task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class FileClonerTest {
    private static final Path TEST_FILE = Path.of("testfile.txt").toAbsolutePath();

    @BeforeAll
    static void createFile() throws IOException {
        Files.createFile(TEST_FILE);
        Files.write(TEST_FILE, List.of(
            "key:value",
            "apple:fruit",
            "hello:world",
            "some:data"
        ));
    }

    @AfterAll
    static void removeFile() throws IOException {
        try (var pathStream = Files.newDirectoryStream(TEST_FILE.getParent(), "testfile*.txt")) {
            pathStream.forEach(path -> {
                try {
                    Files.delete(path);
                } catch (IOException ignored) {
                }
            });
        }
    }

    @Test
    void test_FileCloner() throws IOException {
        Path copy = FileCloner.cloneFile(TEST_FILE);
        assertThat(Files.readAllBytes(copy))
            .isEqualTo(Files.readAllBytes(TEST_FILE));
        assertThat(copy.getFileName().toString())
            .isEqualTo("testfile - копия.txt");
        copy = FileCloner.cloneFile(TEST_FILE);
        assertThat(copy.getFileName().toString())
            .isEqualTo("testfile - копия (2).txt");
    }
}
