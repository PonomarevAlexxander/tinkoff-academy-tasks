package edu.hw6.task2;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class FileCloner {
    private FileCloner() {
    }

    public static Path cloneFile(Path file) throws IOException {
        if (Files.notExists(file) && !Files.isDirectory(file)) {
            throw new IllegalArgumentException("path should exist and be not directory");
        }
        Path filePath = file.toAbsolutePath();
        String dir = filePath.getParent().toString();
        String[] nameAndExtension = filePath.getFileName().toString().split("\\.");

        StringBuilder copyPathBuilder = new StringBuilder(nameAndExtension[0]);
        copyPathBuilder.append(" - копия");
        int shift = copyPathBuilder.length();
        copyPathBuilder.append(".");
        copyPathBuilder.append(nameAndExtension[1]);
        String bracesFormat = " (%d)";

        int counter = 2;
        Path copyTo = Path.of(dir, copyPathBuilder.toString());
        while (Files.exists(copyTo)) {
            copyPathBuilder.insert(shift, String.format(bracesFormat, counter));
            copyTo = Path.of(dir, copyPathBuilder.toString());
            counter++;
        }
        return Files.copy(filePath, copyTo);
    }
}
