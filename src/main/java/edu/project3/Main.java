package edu.project3;

import java.io.IOException;
import java.net.URI;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.LinkedList;
import java.util.List;
import org.apache.commons.cli.CommandLine;
import org.apache.commons.cli.CommandLineParser;
import org.apache.commons.cli.DefaultParser;
import org.apache.commons.cli.ParseException;

public class Main {
    private static final String CMD_CALL_SYNTAX = "java -jar nginx-log-stats.jar";

    private Main() {

    }

    @SuppressWarnings({"RegexpSinglelineJava", "ReturnCount"})
    public static void main(String[] args) {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;
        try {
            cmd = parser.parse(CliOptions.getOptions(), args);
        } catch (ParseException e) {
            CliOptions.printHelp(System.out, CMD_CALL_SYNTAX);
            return;
        }

        List<LogRecord> logs;
        try {
            logs = getLogs(cmd);
        } catch (IOException | InterruptedException e) {
            System.out.println("Error occurred: " + e);
            return;
        }
        logs = filterLogsByTime(cmd, logs);

        LogsAnalyzer analyzer = new LogsAnalyzer(logs);
        LogsPrinter printer = new LogsPrinter(analyzer, System.out);

        String format = cmd.getOptionValue("format", "");
        TableRender render = switch (format) {
            case "markdown" -> new MarkdownTableRender();
            case "adoc" -> new AdocTableRender();
            default -> new AdocTableRender();
        };
        printer.printStatistics(render);
    }

    private static List<LogRecord> getLogs(CommandLine cmd) throws IOException, InterruptedException {
        String[] resources = cmd.getOptionValues("path");

        List<Path> files = new LinkedList<>();
        List<URI> endpoints = new LinkedList<>();
        divideResources(resources, files, endpoints);

        LogParser logParser = new NginxLogParser();
        List<LogsProvider> providers = new LinkedList<>();
        if (!files.isEmpty()) {
            providers.add(new FilesLogsProvider(files, logParser));
        }
        if (!endpoints.isEmpty()) {
            providers.add(new HttpLogsProvider(endpoints, logParser));
        }
        List<LogRecord> logs = new LinkedList<>();
        for (LogsProvider provider : providers) {
            logs.addAll(provider.getLogs());
        }
        return logs;
    }

    private static void divideResources(String[] resources, List<Path> files, List<URI> endpoints) {
        Path current = Path.of("").toAbsolutePath();
        for (String resource : resources) {
            if (resource.startsWith("http")) {
                try {
                    endpoints.add(URI.create(resource));
                } catch (RuntimeException ignore) {
                }
            } else {
                try {
                    files.add(Path.of(resource));
                } catch (RuntimeException ignore) {
                    try (var stream = Files.newDirectoryStream(current, resource)) {
                        stream.forEach(files::add);
                    } catch (IOException ignored) {
                    }
                }
            }
        }
    }

    @SuppressWarnings("MultipleStringLiterals")
    private static List<LogRecord> filterLogsByTime(CommandLine cmd, List<LogRecord> logs) {
        List<LogRecord> result = logs;
        if (cmd.hasOption("from")) {
            OffsetDateTime from = OffsetDateTime.parse(cmd.getOptionValue("from"), DateTimeFormatter.ISO_LOCAL_DATE);
            result = result.stream()
                .filter(log -> log.time().isAfter(from))
                .toList();
        }
        if (cmd.hasOption("to")) {
            OffsetDateTime to = OffsetDateTime.parse(cmd.getOptionValue("to"), DateTimeFormatter.ISO_LOCAL_DATE);
            result = result.stream()
                .filter(log -> log.time().isBefore(to))
                .toList();
        }
        return result;
    }
}
