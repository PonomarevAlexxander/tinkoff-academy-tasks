package edu.project3;

import java.io.PrintStream;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class LogsPrinter {
    private final LogsAnalyzer analyzer;
    private final PrintStream out;
    private static final int MAX_ROWS = 3;

    public LogsPrinter(LogsAnalyzer analyzer, PrintStream out) {
        this.analyzer = analyzer;
        this.out = out;
    }

    public void printStatistics(TableRender render) {
        Map<String, List<List<String>>> tables = Map.of(
            "General information", getGeneralInformation(),
            "Rush hours", getRushHours(),
            "Resources", getResources(),
            "Status codes", getStatusCodes()
        );
        for (var table : tables.entrySet()) {
            out.print(render.render(table.getKey(), table.getValue()));
        }
    }

    private List<List<String>> getGeneralInformation() {
        return List.of(
            List.of("Metric", "Value"),
            List.of("most frequent ip", analyzer.getMostFrequentIp()),
            List.of("begin date", analyzer.getBeginDate().toString()),
            List.of("end date", analyzer.getEndDate().toString()),
            List.of("number of requests", Integer.toString(analyzer.getTotalNumberOfRequests())),
            List.of("average response size in bytes", Integer.toString(analyzer.getAverageResponseSize()))
        );
    }

    private List<List<String>> getRushHours() {
        List<List<String>> rushHours = new LinkedList<>();
        rushHours.add(List.of("Hour", "Number of requests"));
        rushHours.addAll(analyzer.getRushHours().entrySet().stream()
            .map(entry -> List.of(entry.getKey().toString(), entry.getValue().toString()))
            .toList());
        return rushHours;
    }

    private List<List<String>> getResources() {
        return analyzer.getMostFrequentResources(MAX_ROWS).entrySet().stream()
            .map(entry -> List.of(entry.getKey(), entry.getValue().toString()))
            .toList();
    }

    private List<List<String>> getStatusCodes() {
        return analyzer.getMostFrequentStatusCodes(MAX_ROWS).entrySet().stream()
            .map(entry -> List.of(entry.getKey().toString(), entry.getValue().toString()))
            .toList();
    }
}
