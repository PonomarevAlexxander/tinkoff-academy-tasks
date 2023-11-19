package edu.project3;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;

public class FilesLogsProvider implements LogsProvider {
    private final List<Path> files;
    private final LogParser parser;

    public FilesLogsProvider(List<Path> files, LogParser parser) {
        this.files = files.stream()
            .filter(file -> !Files.isDirectory(file))
            .toList();
        this.parser = parser;
    }

    @Override
    public List<LogRecord> getLogs() {
        List<LogRecord> logs = new LinkedList<>();
        for (Path file : files) {
            try (var reader  = Files.newBufferedReader(file, StandardCharsets.UTF_8)) {
                String line = reader.readLine();
                while (line != null) {
                    logs.add(parser.parse(line));
                    line = reader.readLine();
                }
            } catch (IOException e) {
                return null;
            }
        }
        return logs;
    }
}
