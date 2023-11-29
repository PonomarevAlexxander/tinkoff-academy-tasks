package edu.project3;

import java.time.OffsetDateTime;

public record LogRecord(
    String remoteAddress,
    String remoteUser,
    OffsetDateTime time,
    String request,
    int status,
    int bytesSent,
    String httpReferer,
    String httpUserAgent) {

}
