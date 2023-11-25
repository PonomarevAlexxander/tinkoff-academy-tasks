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

public class DiskMap implements Map<String, String> {
    private final HashMap<String, String> data = new HashMap<>();
    private final Path dataFile;

    public DiskMap(Path file) throws IOException {
        this.dataFile = file;
        if (Files.notExists(file)) {
            Files.createDirectory(file);
        } else {
            load();
        }
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
        String result = data.put(key, value);
        save();
        return result;
    }

    @Override
    public String remove(Object key) {
        String result = data.remove(key);
        save();
        return result;
    }

    @Override
    public void putAll(@NotNull Map<? extends String, ? extends String> m) {
        data.putAll(m);
        save();
    }

    @Override
    public void clear() {
        data.clear();
        save();
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

    private void load() {
        List<String> lines;
        try {
            lines = Files.readAllLines(dataFile);
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
        data.putAll(lines.stream()
            .map(this::extractKeyValue)
            .filter(Optional::isPresent)
            .map(Optional::get)
            .collect(Collectors.toMap(Map.Entry::getKey, Map.Entry::getValue)));
    }

    private void save() {
        try {
            Files.write(dataFile, data.entrySet().stream()
                .map(entry -> String.join(":", entry.getKey(), entry.getValue()))
                .toList());
        } catch (IOException e) {
            throw new IllegalStateException(e);
        }
    }

    private Optional<Map.Entry<String, String>> extractKeyValue(String fileLine) {
        String[] keyValue = fileLine.strip().split(":");
        if (keyValue.length == 2) {
            return Optional.of(Map.entry(keyValue[0], keyValue[1]));
        }
        return Optional.empty();
    }
}
