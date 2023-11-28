package edu.project3;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Stream;

public class HttpLogsProvider implements LogsProvider {
    private final List<URI> endpoints;
    private final LogParser parser;

    public HttpLogsProvider(List<URI> endpoints, LogParser parser) {
        this.endpoints = List.copyOf(endpoints);
        this.parser = parser;
    }

    @Override
    public List<LogRecord> getLogs() throws IOException, InterruptedException {
        try (HttpClient client = HttpClient.newHttpClient()) {
            HttpResponse<Stream<String>> response;
            List<LogRecord> logs = new LinkedList<>();
            for (var uri : endpoints) {
                HttpRequest request = HttpRequest.newBuilder()
                    .uri(uri)
                    .GET()
                    .build();

                response = client.send(request, HttpResponse.BodyHandlers.ofLines());
                response.body().forEach(line -> logs.add(parser.parse(line)));
            }
            return logs;
        }
    }
}
