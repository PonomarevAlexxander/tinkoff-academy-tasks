package edu.project3;

import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Locale;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class NginxLogParser implements LogParser {
    private static final Pattern LOG_PATTERN =
        Pattern.compile("^(?<ip>.*)"
            + " - (?<remoteUser>.*) \\[(?<dateTime>.*)] \"(?<request>.*)\" (?<statusCode>\\d{3}) (?<bytes>\\d+)"
            + " \"(?<httpReferer>.*)\" \"(?<httpUserAgent>.*)\"$");
    private static final DateTimeFormatter TIME_FORMAT =
        DateTimeFormatter.ofPattern("dd/MMM/yyyy:HH:mm:ss xx", Locale.US);

    @Override
    public LogRecord parse(String log) {
        Matcher matcher = LOG_PATTERN.matcher(log);
        if (matcher.find()) {
            return new LogRecord(
                matcher.group("ip"),
                matcher.group("remoteUser"),
                OffsetDateTime.parse(matcher.group("dateTime"), TIME_FORMAT),
                matcher.group("request"),
                Integer.parseInt(matcher.group("statusCode")),
                Integer.parseInt(matcher.group("bytes")),
                matcher.group("httpReferer"),
                matcher.group("httpUserAgent")
            );
        }
        return null;
    }
}
