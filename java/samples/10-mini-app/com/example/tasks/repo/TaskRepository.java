package com.example.tasks.repo;

import com.example.tasks.domain.Task;
import java.util.List;
import java.util.Optional;

/**
 * タスク永続化の抽象。
 */
public interface TaskRepository {
    List<Task> findAll();

    Optional<Task> findById(String id);

    void saveAll(List<Task> tasks);
}
