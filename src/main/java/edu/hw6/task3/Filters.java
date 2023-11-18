package edu.hw6.task3;

import java.io.InputStream;
import java.nio.file.FileSystem;
import java.nio.file.Files;
import java.nio.file.PathMatcher;

public class Filters {
    private Filters() {
    }

    public static AbstractFilter readable() {
        return Files::isReadable;
    }

    public static AbstractFilter writable() {
        return Files::isWritable;
    }

    public static AbstractFilter isLargerThan(long bytes) {
        return path -> Files.size(path) > bytes;
    }

    public static AbstractFilter isLessThan(long bytes) {
        return path -> Files.size(path) < bytes;
    }

    public static AbstractFilter fileSignaturesMatch(char... firstBytes) {
        return path -> {
            try (InputStream inputStream = Files.newInputStream(path)) {
                byte[] bytes = inputStream.readNBytes(firstBytes.length);
                for (int index = 0; index < bytes.length; index++) {
                    if (Byte.toUnsignedInt(bytes[index]) != firstBytes[index]) {
                        return false;
                    }
                }
                return true;
            }
        };
    }

    public static AbstractFilter globMatches(String glob) {
        return path -> {
            FileSystem fs = path.getFileSystem();
            final PathMatcher matcher = fs.getPathMatcher("glob:" + glob);
            return matcher.matches(path.getFileName());
        };
    }

    public static AbstractFilter nameMatches(String regex) {
        return path -> path.getFileName().toString().matches(regex);
    }
}
