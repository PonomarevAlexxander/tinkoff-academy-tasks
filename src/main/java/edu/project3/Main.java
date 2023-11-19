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

    @SuppressWarnings("RegexpSinglelineJava")
    public static void main(String[] args) {
        CommandLineParser parser = new DefaultParser();
        CommandLine cmd;
        try {
            cmd = parser.parse(CliOptions.getOptions(), args);
        } catch (ParseException e) {
            CliOptions.printHelp(System.out, CMD_CALL_SYNTAX);
            return;
        }

        String[] resources = cmd.getOptionValues("path");
        String format = cmd.getOptionValue("format", null);

        List<Path> files = new LinkedList<>();
        List<URI> endpoints = new LinkedList<>();
        divideResources(resources, files, endpoints);

        LogParser logParser = new NginxLogParser();
        LogsProvider provider;
        List<LogRecord> logs = new LinkedList<>();
        if (!files.isEmpty()) {
            provider = new FilesLogsProvider(files, logParser);
            logs.addAll(provider.getLogs());
        }
        if (!endpoints.isEmpty()) {
            provider = new HttpLogsProvider(endpoints, logParser);
            logs.addAll(provider.getLogs());
        }
        logs = filterLogsByTime(cmd, logs);

        LogsAnalyzer analyzer = new LogsAnalyzer(logs);
        String header1 = "General information";
        List<List<String>> metrics = List.of(
            List.of("Metric", "Value"),
            List.of("most frequent ip", analyzer.getMostFrequentIp()),
            List.of("begin date", analyzer.getBeginDate().toString()),
            List.of("end date", analyzer.getEndDate().toString()),
            List.of("number of requests", Integer.toString(analyzer.getTotalNumberOfRequests())),
            List.of("average response size in bytes", Integer.toString(analyzer.getAverageResponseSize()))
        );
        TableRender render = switch (format) {
            case "markdown" -> new MarkdownTableRender();
            case "adoc" -> new AdocTableRender();
            default -> new AdocTableRender();
        };
        System.out.print(render.render(header1, metrics));

        String header2 = "Rush hours";
        List<List<String>> rushHours = new LinkedList<>();
        rushHours.add(List.of("Hour", "Number of requests"));
        rushHours.addAll(analyzer.getRushHours().entrySet().stream()
            .map(entry -> List.of(entry.getKey().toString(), entry.getValue().toString()))
            .toList());
        System.out.print(render.render(header2, rushHours));
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
