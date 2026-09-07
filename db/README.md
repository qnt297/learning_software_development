# データベース学習ロードマップ

RDB を実務で扱うための段階的な学習教材です。  
題材は OSS のデファクトに近い **PostgreSQL** と、標準的な SQL を中心にします。

各レッスンは **教材（Markdown）** と **SQL サンプル／演習** で構成されています。

## 想定読者

- なんらかのプログラミング経験がある人（Java トラックと並行でも可）
- Web / 業務アプリでデータを永続化したい人
- 「動く SELECT」だけでなく、制約・トランザクション・設計の入口まで知りたい人

## 前提環境

| 項目 | 推奨 |
|------|------|
| RDBMS | [PostgreSQL](https://www.postgresql.org/) 14 以上（OSS・事実上の定番の一つ） |
| クライアント | `psql`、DBeaver、pgAdmin、VS Code / Cursor の SQL 拡張など |
| 方言 | サンプルは PostgreSQL 向け。多くの基本 SQL は他 RDB にも通じる |

### MySQL / MariaDB を使う場合

基本の SELECT / JOIN / 集計はほぼ同じです。自動増分や一部関数、文字列連結などが違うため、エラーが出たら PostgreSQL ドキュメントまたは方言差分を確認してください。学習の本線は PostgreSQL で問題ありません。

### サンプルの進め方

```bash
# 例: データベース作成後
psql -U postgres -d learning_shop -f db/samples/shop/00_schema.sql
psql -U postgres -d learning_shop -f db/samples/shop/01_seed.sql

# 演習（自分で書いてから answers を見る）
psql -U postgres -d learning_shop -f db/samples/exercises/01_select_questions.sql
```

Windows で `psql` がパスに無い場合は、PostgreSQL のインストールディレクトリ配下の `bin` を PATH に通すか、GUI クライアントでファイル内容を実行してください。

## ロードマップ（全10章）

| # | 章 | 教材 | SQL サンプル | 到達目標 |
|---|----|------|--------------|----------|
| 01 | RDB と SQL の全体像 | [lessons/01-rdbms-intro.md](lessons/01-rdbms-intro.md) | [shop/](samples/shop) | 表・行・主キーの感覚を持つ |
| 02 | DDL・ER・UML | [lessons/02-ddl.md](lessons/02-ddl.md) | `00_schema.sql` | CREATE / 制約 / ER図 / UML対応 |
| 03 | SELECT 基礎 | [lessons/03-select.md](lessons/03-select.md) | [exercises/01_*](samples/exercises) | 絞り込み・並び替え・LIMIT |
| 04 | JOIN | [lessons/04-joins.md](lessons/04-joins.md) | [exercises/02_*](samples/exercises) | 複数表を結合して答えを出す |
| 05 | 集計とサブクエリ | [lessons/05-aggregation.md](lessons/05-aggregation.md) | [exercises/03_*](samples/exercises) | GROUP BY / HAVING / 副問い合わせ |
| 06 | DML と整合性 | [lessons/06-dml.md](lessons/06-dml.md) | [exercises/04_*](samples/exercises) | INSERT/UPDATE/DELETE と制約エラー |
| 07 | インデックスと実行計画 | [lessons/07-indexes.md](lessons/07-indexes.md) | [exercises/05_*](samples/exercises) | 索引の効きと EXPLAIN の入口 |
| 08 | トランザクション | [lessons/08-transactions.md](lessons/08-transactions.md) | [exercises/06_*](samples/exercises) | COMMIT / ROLLBACK / 分離レベルの感覚 |
| 09 | 正規化とモデリング | [lessons/09-normalization.md](lessons/09-normalization.md) | — | 第1〜3正規形の入口 |
| 10 | ビューと次のステップ | [lessons/10-next-steps.md](lessons/10-next-steps.md) | [exercises/07_*](samples/exercises) | VIEW、周辺エコシステムへの橋渡し |

## 題材データ（learning_shop）

通販風の小さなスキーマです。

```text
customers ──< orders ──< order_items >── products
                │
             categories（products が参照）
```

アプリ開発（Java + Spring）と接続するときの定番構造に寄せています。

## ディレクトリ構成

```text
db/
├── README.md              # 本ファイル
├── lessons/               # 教材
└── samples/
    ├── shop/              # スキーマと初期データ
    └── exercises/         # 演習 SQL（questions / answers）
```

## 学習の進め方

1. 教材を読む
2. `00_schema.sql` → `01_seed.sql` を流す
3. `*_questions.sql` を自分で埋める（または新規クエリを書く）
4. `*_answers.sql` で方針を確認する（丸暗記しない）
5. 間違えたら表を `SELECT` で眺めてから再挑戦する

## Java トラックとの関係

- Java の永続化（JDBC / JPA / Spring Data）の前に、このロードマップの 01〜06 を終えると理解が速い
- 概要は [Java: SE/EE と Spring](../java/lessons/11-java-se-ee-and-spring.md) を参照
- Java からの接続は [JDBC とコネクションプール](../java/lessons/12-jdbc-and-pooling.md) を参照

---

次のステップ: [01. RDB と SQL の全体像](lessons/01-rdbms-intro.md)
