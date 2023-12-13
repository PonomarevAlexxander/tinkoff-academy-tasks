package edu.hw8.task1;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class SocketServer implements AutoCloseable {
    private final QuotesService service;
    private final Logger logger = LogManager.getLogger();
    private ServerSocket server;

    public SocketServer(QuotesService service) {
        this.service = service;
    }

    public void start(int port, int threads) throws IOException {
        server = new ServerSocket(port);
        ExecutorService executorService = Executors.newFixedThreadPool(threads);
        logger.info("Server starts");
        while (!server.isClosed()) {
            try {
                Socket clientSocket = server.accept();
                CompletableFuture.runAsync(() -> {
                        try {
                            handleConnection(clientSocket);
                        } catch (IOException e) {
                            throw new RuntimeException(e);
                        }
                    }, executorService)
                    .thenRun(() -> logger.info("Client handled"));
            } catch (Exception e) {
                logger.error("Failed to accept socket");
            }
        }
    }

    private void handleConnection(Socket clientSocket) throws IOException {
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(clientSocket.getInputStream()));
             PrintWriter writer = new PrintWriter(clientSocket.getOutputStream())) {
            String request = reader.readLine();
            if (request != null) {
                String quote = service.findQuoteWith(request.strip());
                writer.println(quote == null ? "Quote not found" : quote);
            }
        } catch (IOException e) {
            logger.error(e);
        } finally {
            clientSocket.close();
        }
    }

    @Override
    public void close() throws Exception {
        server.close();
    }
}
