package edu.hw6.task1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

class DiskMapTest {
    private static final Path TEST_FILE = Path.of("testfile.txt");

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
        Files.delete(TEST_FILE);
    }

    @Test
    void test_DiskMap() throws IOException {
        DiskMap kvData = new DiskMap(TEST_FILE);
        assertThat(kvData.entrySet())
            .isEqualTo(Set.of(
                Map.entry("key", "value"),
                Map.entry("apple", "fruit"),
                Map.entry("hello", "world"),
                Map.entry("some", "data")
            ));
        kvData.remove("apple");
        kvData.put("new", "data");
        assertThat(kvData.entrySet())
            .isEqualTo(Set.of(
                Map.entry("key", "value"),
                Map.entry("hello", "world"),
                Map.entry("new", "data"),
                Map.entry("some", "data")
            ));
    }
}
