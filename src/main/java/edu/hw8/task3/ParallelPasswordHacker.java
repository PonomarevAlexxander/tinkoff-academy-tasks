package edu.hw8.task3;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class ParallelPasswordHacker {
    public Map<String, String> hackPasswords(Map<String, String> encodedPasswords, int threads) {
        Map<String, String> passHashAndUsername = encodedPasswords.entrySet().stream()
            .collect(Collectors.toConcurrentMap(Map.Entry::getValue, Map.Entry::getKey));
        Map<String, String> usernameAndPassword = new ConcurrentHashMap<>();
        try (ExecutorService service = Executors.newFixedThreadPool(threads)) {
            Queue<Task> tasks = new ConcurrentLinkedQueue<>();

            Stream.generate(() -> CompletableFuture.runAsync(new Hacker(
                    tasks,
                    usernameAndPassword,
                    passHashAndUsername
                ), service))
                .limit(threads)
                .forEach(future -> {
                    try {
                        future.get();
                    } catch (InterruptedException | ExecutionException e) {
                        throw new RuntimeException(e);
                    }
                });
        }
        return usernameAndPassword;
    }

    private record Task(String from, String to) {
    }

    private static class Hacker implements Runnable {
        private final SimplePasswordGenerator generator = new SimplePasswordGenerator();
        private final Map<String, String> resultMap;
        private final Map<String, String> data;
        private final Queue<Task> tasks;
        private String password;
        private String beforePassword;

        Hacker(Queue<Task> tasks, Map<String, String> resultMap, Map<String, String> data) {
            this.resultMap = resultMap;
            this.data = data;
            this.tasks = tasks;
        }

        @Override
        public void run() {
            updateTask();
            while (!data.isEmpty()) {
                password = generator.generateNextPassword(password);
                if (password.equals(beforePassword)) {
                    updateTask();
                }
                String md5Hash = Md5HashEncoder.getMd5Hash(password);
                String username = data.get(md5Hash);
                if (username != null) {
                    resultMap.put(username, password);
                    data.remove(md5Hash);
                }
            }
        }

        private void updateTask() {
            Task task = tasks.poll();
            if (task == null) {
                password = "";
                beforePassword = "00";
            } else {
                password = task.from;
                beforePassword = task.to;
            }
            String next = generator.generateNextPassword(beforePassword);
            Character replaceWith = generator.getAlphabet().higher(next.charAt(0));
            tasks.add(new Task(beforePassword, next.replace(next.charAt(0), replaceWith)));
        }
    }
}
