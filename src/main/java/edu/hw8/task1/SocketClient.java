package edu.hw8.task1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.Socket;
import java.util.Objects;
import java.util.stream.Collectors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SocketClient {
    private final String host;
    private final int port;
    private final Logger logger = LogManager.getLogger();

    public SocketClient(String host, int port) {
        Objects.requireNonNull(host);
        this.host = host;
        this.port = port;
    }

    public String getQuoteByTheme(String theme) {
        while (true) {
            try {
                return tryToGetQuote(theme);
            } catch (IOException e) {
                logger.error("Failed to get quote");
            }
        }
    }

    private String tryToGetQuote(String theme) throws IOException {
        try (Socket client = new Socket(host, port);
             PrintWriter writer = new PrintWriter(client.getOutputStream());
             BufferedReader reader = new BufferedReader(new InputStreamReader(client.getInputStream()))) {
            logger.info("Client connected");
            writer.println(theme);
            writer.flush();
            return reader.lines()
                .collect(Collectors.joining("\n"));
        }
    }
}
