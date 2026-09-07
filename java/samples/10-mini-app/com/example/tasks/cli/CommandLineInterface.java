package com.example.tasks.cli;

import com.example.tasks.domain.Task;
import com.example.tasks.service.TaskService;
import java.util.Scanner;

/**
 * コンソール UI。入力の解釈だけを担当する。
 */
public class CommandLineInterface {
    private final TaskService taskService;

    public CommandLineInterface(TaskService taskService) {
        this.taskService = taskService;
    }

    public void run(Scanner scanner) {
        printHelp();
        while (true) {
            System.out.print("> ");
            if (!scanner.hasNextLine()) {
                break;
            }
            String line = scanner.nextLine().trim();
            if (line.isEmpty()) {
                continue;
            }
            if ("exit".equals(line)) {
                System.out.println("bye");
                break;
            }
            handle(line);
        }
    }

    private void handle(String line) {
        try {
            if (line.equals("help")) {
                printHelp();
                return;
            }
            if (line.equals("list")) {
                for (Task task : taskService.list()) {
                    System.out.println(task);
                }
                return;
            }
            if (line.startsWith("add ")) {
                Task created = taskService.add(line.substring(4).trim());
                System.out.println("added: " + created);
                return;
            }
            if (line.startsWith("done ")) {
                Task updated = taskService.markDone(line.substring(5).trim());
                System.out.println("updated: " + updated);
                return;
            }
            if (line.startsWith("remove ")) {
                String id = line.substring(7).trim();
                taskService.remove(id);
                System.out.println("removed: " + id);
                return;
            }
            System.out.println("unknown command. type 'help'");
        } catch (RuntimeException e) {
            System.err.println("error: " + e.getMessage());
        }
    }

    private void printHelp() {
        System.out.println("""
                Commands:
                  add <title>   タスク追加
                  list          一覧
                  done <id>     完了
                  remove <id>   削除
                  help          ヘルプ
                  exit          終了
                """);
    }
}
