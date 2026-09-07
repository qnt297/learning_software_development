package com.example.tasks.service;

import com.example.tasks.domain.Task;
import com.example.tasks.repo.TaskRepository;
import java.util.ArrayList;
import java.util.List;

/**
 * タスク操作のユースケース。
 */
public class TaskService {
    private final TaskRepository repository;

    public TaskService(TaskRepository repository) {
        this.repository = repository;
    }

    public Task add(String title) {
        List<Task> tasks = new ArrayList<>(repository.findAll());
        Task created = new Task(title);
        tasks.add(created);
        repository.saveAll(tasks);
        return created;
    }

    public List<Task> list() {
        return repository.findAll();
    }

    public Task markDone(String id) {
        List<Task> tasks = new ArrayList<>(repository.findAll());
        Task target = findOrThrow(tasks, id);
        target.markDone();
        repository.saveAll(tasks);
        return target;
    }

    public void remove(String id) {
        List<Task> tasks = new ArrayList<>(repository.findAll());
        Task target = findOrThrow(tasks, id);
        tasks.remove(target);
        repository.saveAll(tasks);
    }

    private Task findOrThrow(List<Task> tasks, String id) {
        return tasks.stream()
                .filter(task -> task.getId().equals(id))
                .findFirst()
                .orElseThrow(() -> new IllegalArgumentException("task not found: " + id));
    }
}
