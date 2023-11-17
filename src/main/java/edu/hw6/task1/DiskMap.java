package edu.hw6.task1;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

public class DiskMap implements Map<String, String>, AutoCloseable {
    private final HashMap<String, String> data = new HashMap<>();
    private final Path dataFile;

    public DiskMap(Path file) throws IOException {
        this.dataFile = file;
        if (Files.notExists(file)) {
            Files.createDirectory(file);
        } else {
            mergeDataFrom(dataFile);
        }
    }

    public void mergeDataFrom(Path file) throws IOException {
        List<String> lines = Files.readAllLines(file);
        data.putAll(lines.stream()
            .map(this::extractKeyValue)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

    public void saveAs(Path file) throws IOException {
        Files.write(file, data.entrySet().stream()
            .map(entry -> String.join(":", entry.getKey(), entry.getValue()))
            .toList());
    }

    @Override
    public int size() {
        return data.size();
    }

    @Override
    public boolean isEmpty() {
        return data.isEmpty();
    }

    @Override
    public boolean containsKey(Object key) {
        return data.containsKey(key);
    }

    @Override
    public boolean containsValue(Object value) {
        return data.containsValue(value);
    }

    @Override
    public String get(Object key) {
        return data.get(key);
    }

    @Nullable
    @Override
    public String put(String key, String value) {
        return data.put(key, value);
    }

    @Override
    public String remove(Object key) {
        return data.remove(key);
    }

    @Override
    public void putAll(@NotNull Map<? extends String, ? extends String> m) {
        data.putAll(m);
    }

    @Override
    public void clear() {
        data.clear();
    }

    @NotNull
    @Override
    public Set<String> keySet() {
        return data.keySet();
    }

    @NotNull
    @Override
    public Collection<String> values() {
        return data.values();
    }

    @NotNull
    @Override
    public Set<Entry<String, String>> entrySet() {
        return data.entrySet();
    }

    private Optional<Map.Entry<String, String>> extractKeyValue(String fileLine) {
        String[] keyValue = fileLine.strip().split(":");
        if (keyValue.length == 2) {
            return Optional.of(Map.entry(keyValue[0], keyValue[1]));
        }
        return Optional.empty();
    }

    @Override
    public void close() throws IOException {
        saveAs(dataFile);
    }
}
