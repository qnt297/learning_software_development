# 07. インデックスと実行計画

## 目次

- [この章の目標](#この章の目標)
- [学習ポイント](#学習ポイント)
- [演習](#演習)
- [次の章](#次の章)

## この章の目標

- インデックスが効く／効かない典型を説明できる
- `EXPLAIN` / `EXPLAIN ANALYZE` の入口を知る

## 学習ポイント

- 索引は「読みの高速化」と「書きのコスト増・容量増」のトレードオフ
- 主キー・外部キー参照・よく検索する列が候補
- 低カーディナリティ（真偽だけ等）や、ほぼ全件スキャンになる条件では効果が薄いことがある
- 過度な索引は INSERT/UPDATE を遅くする

```sql
EXPLAIN ANALYZE
SELECT * FROM orders WHERE customer_id = 1;
```

## 演習

- 問題: `samples/exercises/05_index_questions.sql`
- 解答例: `samples/exercises/05_index_answers.sql`

## 次の章

[08. トランザクション](08-transactions.md)
