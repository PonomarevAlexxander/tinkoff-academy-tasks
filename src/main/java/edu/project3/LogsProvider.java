package edu.project3;

import java.io.IOException;
import java.util.List;

public interface LogsProvider {
    List<LogRecord> getLogs() throws IOException, InterruptedException;
}
