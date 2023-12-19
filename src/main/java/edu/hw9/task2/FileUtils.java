package edu.hw9.task2;

import java.io.IOException;
import java.nio.file.DirectoryStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;
import java.util.concurrent.RecursiveTask;
import java.util.function.Predicate;

public class FileUtils {
    private FileUtils() {
    }

    public static List<Path> getAllFiles(Path directory, Predicate<Path> predicate, int threads) {
        if (!Files.isDirectory(directory)) {
            throw new IllegalArgumentException("expected directory");
        }
        try (ForkJoinPool pool = new ForkJoinPool(threads)) {
            return pool.invoke(new FilesFilter(directory, predicate));
        }
    }

    public static List<Path> getFilesWithSize(Path directory, long size, int threads) {
        return getAllFiles(directory, path -> {
            try {
                return Files.size(path) == size;
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }, threads);
    }

    public static List<Path> getFilesWithExtension(Path directory, String extension, int threads) {
        return getAllFiles(directory, path -> path.getFileName().toString().endsWith(extension), threads);
    }

    public static List<Path> getAllDirsWithMoreNFiles(Path directory, int nFiles, int threads) {
        if (!Files.isDirectory(directory)) {
            throw new IllegalArgumentException("expected directory in path");
        }
        try (ForkJoinPool pool = new ForkJoinPool(threads)) {
            return pool.invoke(new FilesCounter(directory, nFiles));
        }
    }

    private static class FilesFilter extends RecursiveTask<List<Path>> {
        private final Path currentDirectory;
        private final Predicate<Path> prediacte;

        private FilesFilter(Path currentDirectory, Predicate<Path> predicate) {
            this.currentDirectory = currentDirectory;
            this.prediacte = predicate;
        }

        @Override
        protected List<Path> compute() {
            List<Path> files = new LinkedList<>();
            List<ForkJoinTask<List<Path>>> tasks = new LinkedList<>();
            try (DirectoryStream<Path> paths = Files.newDirectoryStream(currentDirectory)) {
                for (var path : paths) {
                    if (Files.isDirectory(path)) {
                        tasks.add(new FilesFilter(path, prediacte).fork());
                    } else if (prediacte.test(path)) {
                        files.add(path);
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            files.addAll(tasks.stream()
                .map(ForkJoinTask::join)
                .flatMap(List::stream)
                .toList());
            return files;
        }
    }

    private static class FilesCounter extends RecursiveTask<List<Path>> {
        private final Path currentDirectory;
        private final long minFilesNumber;

        private FilesCounter(Path currentDirectory, long minNumber) {
            this.currentDirectory = currentDirectory;
            this.minFilesNumber = minNumber;
        }

        @Override
        protected List<Path> compute() {
            List<ForkJoinTask<List<Path>>> tasks = new LinkedList<>();
            long nFiles = 0;
            try (DirectoryStream<Path> paths = Files.newDirectoryStream(currentDirectory)) {
                for (var path : paths) {
                    if (Files.isDirectory(path)) {
                        tasks.add(new FilesCounter(path, minFilesNumber).fork());
                    } else {
                        nFiles++;
                    }
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            List<Path> dirs = new LinkedList<>(tasks.stream()
                .map(ForkJoinTask::join)
                .flatMap(List::stream)
                .toList());
            if (nFiles > minFilesNumber) {
                dirs.add(currentDirectory);
            }
            return dirs;
        }
    }
}
