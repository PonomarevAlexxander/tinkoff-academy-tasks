package edu.project3;

import java.io.OutputStream;
import java.io.PrintWriter;
import org.apache.commons.cli.HelpFormatter;
import org.apache.commons.cli.Option;
import org.apache.commons.cli.Options;

public class CliOptions {
    private static final int WIDTH = 90;
    private static final int LEFT_PAD = 3;
    private static final int DESC_PAD = 5;
    private static final Options OPTIONS = new Options();

    static {
        Option paths = Option.builder("path")
            .desc("path(s) to analyze, example: -path log1 log2")
            .hasArgs()
            .required()
            .build();
        Option timeFrom = Option.builder("from")
            .desc("time in ISO8601 format after which logs are analyzed")
            .hasArg()
            .required(false)
            .build();
        Option timeTo = Option.builder("to")
            .desc("time in ISO8601 format before which logs are analyzed")
            .hasArg()
            .required(false)
            .build();
        Option format = Option.builder("format")
            .desc("output format (markdown/adoc)")
            .hasArg()
            .required(false)
            .build();
        OPTIONS.addOption(paths);
        OPTIONS.addOption(timeFrom);
        OPTIONS.addOption(timeTo);
        OPTIONS.addOption(format);
    }

    private CliOptions() {
    }

    public static void printHelp(OutputStream out, String callSyntax) {
        HelpFormatter helpFormatter = new HelpFormatter();
        try (PrintWriter writer = new PrintWriter(out)) {
            helpFormatter.printHelp(
                writer,
                WIDTH,
                callSyntax,
                "Options:",
                OPTIONS,
                LEFT_PAD,
                DESC_PAD,
                "---help---",
                true
            );
        }
    }

    public static Options getOptions() {
        return OPTIONS;
    }
}
