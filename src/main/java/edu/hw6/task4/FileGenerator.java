package edu.hw6.task4;

import java.io.BufferedOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.zip.Adler32;
import java.util.zip.CheckedOutputStream;
import java.util.zip.Checksum;

public class FileGenerator {
    private FileGenerator() {

    }

    public static void generateFile(Path file, String text) throws IOException {
        Path createdFile = Files.createFile(file);
        Checksum checksum = new Adler32();
        try (OutputStream stream = Files.newOutputStream(createdFile);
             CheckedOutputStream checkedOutputStream = new CheckedOutputStream(stream, checksum);
             BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(checkedOutputStream);
             OutputStreamWriter outputStreamWriter = new OutputStreamWriter(
                 bufferedOutputStream,
                 StandardCharsets.UTF_8
             );
             PrintWriter printWriter = new PrintWriter(outputStreamWriter)) {
            printWriter.print(text);
        }
    }
}
