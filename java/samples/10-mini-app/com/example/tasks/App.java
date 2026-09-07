package com.example.tasks;

import com.example.tasks.cli.CommandLineInterface;
import com.example.tasks.repo.FileTaskRepository;
import com.example.tasks.service.TaskService;
import java.nio.file.Path;
import java.util.Scanner;

/**
 * タスク管理 CLI の起動クラス。
 *
 * 実行例 (PowerShell):
 *   cd java/samples
 *   javac `
 *     10-mini-app/com/example/tasks/domain/Task.java `
 *     10-mini-app/com/example/tasks/repo/TaskRepository.java `
 *     10-mini-app/com/example/tasks/repo/FileTaskRepository.java `
 *     10-mini-app/com/example/tasks/service/TaskService.java `
 *     10-mini-app/com/example/tasks/cli/CommandLineInterface.java `
 *     10-mini-app/com/example/tasks/App.java
 *   java -cp 10-mini-app com.example.tasks.App
 */
public class App {
    public static void main(String[] args) {
        Path storage = Path.of("tasks-data", "tasks.txt");
        TaskService service = new TaskService(new FileTaskRepository(storage));
        CommandLineInterface cli = new CommandLineInterface(service);

        System.out.println("Task Manager CLI");
        System.out.println("storage: " + storage.toAbsolutePath());
        try (Scanner scanner = new Scanner(System.in)) {
            cli.run(scanner);
        }
    }
}
