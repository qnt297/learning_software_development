# Java 開発学習ロードマップ

ソフトウェア開発者として Java を使いこなすための段階的な学習教材です。  
各レッスンは **教材（Markdown）** と **実行可能なサンプルコード** で構成されています。

## 想定読者

- プログラミングの基礎（変数・条件分岐・ループ）を少しでも触ったことがある人
- Java でアプリケーションを書けるようになりたい人
- 「動くコード」だけでなく、設計・テスト・運用の入口まで学びたい人

## 前提環境

| 項目 | 推奨 |
|------|------|
| JDK | 17 以上（LTS） |
| エディタ | IntelliJ IDEA / VS Code / Cursor |
| ビルド | まずは `javac` / `java`。後半で Maven の考え方に触れる |

### サンプルの実行方法

```bash
# 例: 01-basics の HelloWorld
cd java/samples/01-basics
javac HelloWorld.java
java HelloWorld
```

パッケージ付きのサンプルは、`samples` 直下からコンパイルしてください。

```bash
cd java/samples
javac 04-exceptions/com/example/exceptions/*.java
java com.example.exceptions.ExceptionDemo
```

## ロードマップ（全13章）

| # | 章 | 教材 | サンプル | 到達目標 |
|---|----|------|----------|----------|
| 00 | 実行の仕組み | [lessons/00-runtime.md](lessons/00-runtime.md) | [samples/00-runtime](samples/00-runtime) | JVM・ヒープ・スコープ・main の意味を説明できる |
| 01 | 基礎文法 | [lessons/01-basics.md](lessons/01-basics.md) | [samples/01-basics](samples/01-basics) | 変数・if/for/switch/break・メソッドで小さなプログラムを書く |
| 02 | オブジェクト指向 | [lessons/02-oop.md](lessons/02-oop.md) | [samples/02-oop](samples/02-oop) | クラス・継承・IF・アノテーション |
| 03 | コレクションとジェネリクス | [lessons/03-collections.md](lessons/03-collections.md) | [samples/03-collections](samples/03-collections) | 配列・List/Map/Set・Iterator |
| 04 | 例外処理 | [lessons/04-exceptions.md](lessons/04-exceptions.md) | [samples/04-exceptions](samples/04-exceptions) | 失敗を想定した堅牢なコードを書く |
| 05 | 入出力とリソース管理 | [lessons/05-io.md](lessons/05-io.md) | [samples/05-io](samples/05-io) | ファイル I/O・try-with-resources・Logger |
| 06 | ラムダと Stream API | [lessons/06-streams.md](lessons/06-streams.md) | [samples/06-streams](samples/06-streams) | 宣言的なデータ処理を書く |
| 07 | パッケージとプロジェクト構成 | [lessons/07-project-structure.md](lessons/07-project-structure.md) | [samples/07-project](samples/07-project) | 保守しやすいディレクトリ構成を理解する |
| 08 | ユニットテスト入門 | [lessons/08-testing.md](lessons/08-testing.md) | [samples/08-testing](samples/08-testing) | 振る舞いをテストで固定する |
| 09 | 設計の基礎 | [lessons/09-design.md](lessons/09-design.md) | [samples/09-design](samples/09-design) | SOLID・GoF 全23（参考）・疎結合 |
| 10 | ミニアプリケーション | [lessons/10-mini-app.md](lessons/10-mini-app.md) | [samples/10-mini-app](samples/10-mini-app) | これまでの知識を統合した CLI アプリを完成させる |
| 11 | Java SE/EE と Spring | [lessons/11-java-se-ee-and-spring.md](lessons/11-java-se-ee-and-spring.md) | —（概要） | SE/EE の違いと Spring Framework / Boot の地図 |
| 12 | JDBC とコネクションプール | [lessons/12-jdbc-and-pooling.md](lessons/12-jdbc-and-pooling.md) | —（概念＋接続例） | JDBC・PreparedStatement・HikariCP |

関連トラック: [データベース学習ロードマップ](../db/README.md)

## 推奨学習ペース

- **週 3〜5 時間** なら、約 4〜6 週間で一通り完走できる想定です
- 各章で「教材を読む → サンプルを動かす → 章末課題を自分で書く」の順がおすすめです
- 詰まったらサンプルを改変して挙動を観察してください（写経だけで終わらないことが重要です）

## ディレクトリ構成

```text
java/
├── README.md                 # 本ファイル（ロードマップ）
├── lessons/                  # 教材（Markdown）00〜12
└── samples/                  # 各章の Java サンプル
    ├── 00-runtime/
    ├── 01-basics/
    ├── 02-oop/
    ├── ...
    └── 10-mini-app/
```

## 学習の進め方（共通ルール）

1. **動かす**: まずサンプルをコンパイル・実行する
2. **読む**: 教材で「なぜそう書くか」を確認する
3. **壊す**: わざとエラーを起こしてメッセージを読む
4. **書く**: 章末課題を、サンプルを見ずに実装する
5. **振り返る**: 「自分ならどう設計するか」を短くメモする

---

次のステップ: [00. Java の実行の仕組み](lessons/00-runtime.md)
