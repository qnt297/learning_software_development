package com.example.tasks.domain;

import java.util.Objects;
import java.util.UUID;

/**
 * タスクのドメインモデル。
 */
public class Task {
    private final String id;
    private final String title;
    private boolean done;

    public Task(String title) {
        this(UUID.randomUUID().toString().substring(0, 8), title, false);
    }

    public Task(String id, String title, boolean done) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("title must not be blank");
        }
        this.id = Objects.requireNonNull(id, "id");
        this.title = title.trim();
        this.done = done;
    }

    public String getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public boolean isDone() {
        return done;
    }

    public void markDone() {
        if (done) {
            throw new IllegalStateException("task already done: " + id);
        }
        done = true;
    }

    public String toStorageLine() {
        return id + "\t" + (done ? "1" : "0") + "\t" + title;
    }

    public static Task fromStorageLine(String line) {
        String[] parts = line.split("\t", 3);
        if (parts.length != 3) {
            throw new IllegalArgumentException("invalid line: " + line);
        }
        return new Task(parts[0], parts[2], "1".equals(parts[1]));
    }

    @Override
    public String toString() {
        return "[" + id + "] " + (done ? "DONE" : "TODO") + " " + title;
    }
}
