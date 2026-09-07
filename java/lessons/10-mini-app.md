# 10. ミニアプリケーション：タスク管理 CLI

## 目次

- [この章の目標](#この章の目標)
- [アプリ概要](#アプリ概要)
- [設計の見どころ](#設計の見どころ)
- [サンプル構成](#サンプル構成)
- [実行方法](#実行方法)
- [章末課題（拡張アイデア）](#章末課題拡張アイデア)
- [完走後の次のステップ](#完走後の次のステップ)
- [チェックリスト](#チェックリスト)

## この章の目標

- 01〜09 の知識を統合し、小さな完成アプリを動かす
- ドメイン / サービス / 永続化 / UI（CLI）を分けて実装する
- 機能追加の手順を一通り体験する

## アプリ概要

コンソールで動く **タスク管理ツール** です。

| コマンド | 意味 |
|----------|------|
| `add <title>` | タスク追加 |
| `list` | 一覧表示 |
| `done <id>` | 完了にする |
| `remove <id>` | 削除 |
| `help` | ヘルプ |
| `exit` | 終了 |

タスクはファイルに保存され、再起動後も残ります。

## 設計の見どころ

```text
cli (UI)
  → TaskService (ユースケース)
      → Task (ドメイン)
      → TaskRepository (抽象)
           ↑
      FileTaskRepository (インフラ)
```

- UI は文字列の入出力だけに集中
- 業務ルール（完了済みは二重完了できない等）はサービス／ドメインへ
- 保存形式の詳細はリポジトリ実装へ

## サンプル構成

```text
samples/10-mini-app/
└── com/example/tasks/
    ├── App.java
    ├── domain/Task.java
    ├── service/TaskService.java
    ├── repo/TaskRepository.java
    ├── repo/FileTaskRepository.java
    └── cli/CommandLineInterface.java
```

## 実行方法

```bash
cd java/samples
javac 10-mini-app/com/example/tasks/**/*.java
# Windows PowerShell では次のように列挙しても可
javac `
  10-mini-app/com/example/tasks/domain/Task.java `
  10-mini-app/com/example/tasks/repo/TaskRepository.java `
  10-mini-app/com/example/tasks/repo/FileTaskRepository.java `
  10-mini-app/com/example/tasks/service/TaskService.java `
  10-mini-app/com/example/tasks/cli/CommandLineInterface.java `
  10-mini-app/com/example/tasks/App.java

java -cp 10-mini-app com.example.tasks.App
```

## 章末課題（拡張アイデア）

1. `find <keyword>` でタイトル検索を追加する
2. 完了タスクだけ / 未完了だけを `list` のオプションで切り替える
3. 保存先を切り替えられるよう、メモリ実装リポジトリを追加する
4. `TaskService` のユニットテストを 08 章の方式で書く

## 完走後の次のステップ

- Maven / Gradle プロジェクト化
- JUnit 5 + AssertJ
- Spring Boot で REST API 化
- DB（PostgreSQL 等）への永続化
- ログ・設定・エラーハンドリングの標準化

ここまで来たら、「文法の学習」から「プロダクト開発の学習」へ移る準備ができています。

次の概要: [11. Java SE / EE と Spring](11-java-se-ee-and-spring.md)  
DB 基礎: [データベース学習ロードマップ](../../db/README.md)

## チェックリスト

- [ ] アプリを起動し、追加・一覧・完了・削除ができた
- [ ] 再起動後もデータが残ることを確認した
- [ ] どこに何の責務があるか説明できる
