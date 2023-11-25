package edu.project3;

import java.time.OffsetDateTime;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

public class LogsAnalyzer {
    private final List<LogRecord> logs;

    public LogsAnalyzer(List<LogRecord> logs) {
        this.logs = List.copyOf(logs);
    }

    public OffsetDateTime getBeginDate() {
        return logs.stream()
            .map(LogRecord::time)
            .min(Comparator.naturalOrder())
            .orElse(null);
    }

    public OffsetDateTime getEndDate() {
        return logs.stream()
            .map(LogRecord::time)
            .max(Comparator.naturalOrder())
            .orElse(null);
    }

    public Map<Integer, Long> getRushHours() {
        return logs.stream()
            .map(LogRecord::time)
            .collect(Collectors.groupingBy(OffsetDateTime::getHour, Collectors.counting()));
    }

    public String getMostFrequentIp() {
        return logs.stream()
            .map(LogRecord::remoteAddress)
            .collect(Collectors.groupingBy(Function.identity(), Collectors.counting()))
            .entrySet().stream()
            .max(Map.Entry.comparingByValue())
            .orElse(Map.entry("", 0L))
            .getKey();
    }

    public int getTotalNumberOfRequests() {
        return logs.size();
    }

    public Map<String, Long> getMostFrequentResources(int firstN) {
        return logs.stream()
            .collect(Collectors.groupingBy(this::extractResource, Collectors.counting()))
            .entrySet().stream()
            .sorted((l, r) -> r.getValue().compareTo(l.getValue()))
            .limit(firstN)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public Map<Integer, Long> getMostFrequentStatusCodes(int n) {
        return logs.stream()
            .collect(Collectors.groupingBy(LogRecord::status, Collectors.counting()))
            .entrySet().stream()
            .sorted((l, r) -> r.getValue().compareTo(l.getValue()))
            .limit(n)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue));
    }

    public int getAverageResponseSize() {
        return (int) Math.ceil(logs.stream()
            .mapToInt(LogRecord::bytesSent)
            .average()
            .orElse(0));
    }

    private String extractResource(LogRecord log) {
        return log.request().split(" ")[1];
    }
}
