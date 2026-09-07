# 10. ビューと次のステップ

## 目次

- [この章の目標](#この章の目標)
- [学習ポイント](#学習ポイント)
  - [次のステップ（OSS エコシステム）](#次のステップoss-エコシステム)
- [演習](#演習)
- [完走チェック](#完走チェック)

## この章の目標

- `VIEW` の用途を説明できる
- RDB 学習の「次に学ぶこと」の地図を持つ

## 学習ポイント

```sql
CREATE VIEW order_summaries AS
SELECT o.id AS order_id, c.name AS customer_name, o.ordered_at
FROM orders o
JOIN customers c ON c.id = o.customer_id;
```

ビューは複雑な SELECT に名前を付け、権限や公開面を制御するのにも使えます。

### 次のステップ（OSS エコシステム）

| 領域 | デファクト寄り |
|------|----------------|
| RDB | PostgreSQL（本ロードマップ） |
| マイグレーション | Flyway / Liquibase |
| 接続プール | HikariCP（Spring Boot 既定） |
| ORM / 永続化 | JPA + Hibernate、または MyBatis |
| キャッシュ | Redis |
| 分析 | ウィンドウ関数、マテビュー、倉庫（別トラック） |

Java から接続する場合は [Spring 概要](../../java/lessons/11-java-se-ee-and-spring.md) と合わせて進めてください。

## 演習

- 問題: `samples/exercises/07_view_questions.sql`
- 解答例: `samples/exercises/07_view_answers.sql`

## 完走チェック

- [ ] スキーマを読んで関係を説明できる
- [ ] JOIN と集計で業務質問に答えられる
- [ ] 制約・トランザクションの必要性を説明できる
- [ ] 索引と EXPLAIN の入口に触れた
