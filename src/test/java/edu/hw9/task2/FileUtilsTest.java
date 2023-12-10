package edu.hw9.task2;

import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class FileUtilsTest {
    @TempDir
    static Path tempDir;

    @BeforeAll
    static void initTempRoot() throws IOException {
        Path img = tempDir.resolve("img.jpg");
        Files.write(img, new byte[] {});
        Path text = tempDir.resolve("text.txt");
        Files.write(text, new byte[] {123, 123, 123});
        Path some = tempDir.resolve("somepdf.pdf");
        Files.write(some, new byte[] {123, 123, 123});
        tempDir.resolve("inner").toFile().mkdir();
        Path innerFile = tempDir.resolve("inner").resolve("inner.txt");
        Files.write(innerFile, new byte[] {123, 123, 123});
    }

    @Test
    void test_getAllFiles() {
        List<Path> allFiles = FileUtils.getAllFiles(tempDir, path -> true, 4);
        assertThat(allFiles)
            .asList()
            .size()
            .isEqualTo(4);
    }

    @Test
    void test_getFilesWithSize() throws IOException {
        long size = Files.size(tempDir.resolve("text.txt"));
        List<Path> allFiles = FileUtils.getFilesWithSize(tempDir, size, 4);
        assertThat(allFiles)
            .asList()
            .containsOnly(
                tempDir.resolve("text.txt"),
                tempDir.resolve("somepdf.pdf"),
                tempDir.resolve("inner").resolve("inner.txt")
            );
    }

    @Test
    void test_getFilesWithExtension() throws IOException {
        List<Path> allFiles = FileUtils.getFilesWithExtension(tempDir, ".txt", 4);
        assertThat(allFiles)
            .asList()
            .containsOnly(
                tempDir.resolve("text.txt"),
                tempDir.resolve("inner").resolve("inner.txt")
            );
    }

    @Test
    void test_getAllDirsWithMoreNFiles() {
        List<Path> allFiles = FileUtils.getAllDirsWithMoreNFiles(tempDir, 1, 4);
        assertThat(allFiles)
            .asList()
            .containsOnly(
                tempDir
            );
    }
}
