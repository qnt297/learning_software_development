package com.example.tasks.repo;

import com.example.tasks.domain.Task;
import java.io.IOException;
import java.io.UncheckedIOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * テキストファイルへ保存するリポジトリ実装。
 */
public class FileTaskRepository implements TaskRepository {
    private final Path file;

    public FileTaskRepository(Path file) {
        this.file = file;
    }

    @Override
    public List<Task> findAll() {
        try {
            if (!Files.exists(file)) {
                return List.of();
            }
            List<Task> tasks = new ArrayList<>();
            for (String line : Files.readAllLines(file, StandardCharsets.UTF_8)) {
                if (!line.isBlank()) {
                    tasks.add(Task.fromStorageLine(line));
                }
            }
            return tasks;
        } catch (IOException e) {
            throw new UncheckedIOException("failed to read tasks", e);
        }
    }

    @Override
    public Optional<Task> findById(String id) {
        return findAll().stream()
                .filter(task -> task.getId().equals(id))
                .findFirst();
    }

    @Override
    public void saveAll(List<Task> tasks) {
        try {
            Path parent = file.getParent();
            if (parent != null) {
                Files.createDirectories(parent);
            }
            List<String> lines = new ArrayList<>();
            for (Task task : tasks) {
                lines.add(task.toStorageLine());
            }
            Files.write(file, lines, StandardCharsets.UTF_8);
        } catch (IOException e) {
            throw new UncheckedIOException("failed to write tasks", e);
        }
    }
}
