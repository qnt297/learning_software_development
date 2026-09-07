# 06. DML と整合性

## 目次

- [この章の目標](#この章の目標)
- [学習ポイント](#学習ポイント)
- [演習](#演習)
- [次の章](#次の章)

## この章の目標

- `INSERT` / `UPDATE` / `DELETE` を安全に書ける
- 制約違反エラーの意味を読み取れる

## 学習ポイント

```sql
INSERT INTO customers (name, email) VALUES ('Dave', 'dave@example.com');
UPDATE products SET unit_price = 1200 WHERE id = 1;
DELETE FROM order_items WHERE order_id = 999; -- 存在する ID で試す
```

注意:

- `UPDATE` / `DELETE` は `WHERE` 忘れが事故の元。まず `SELECT` で対象確認
- 外部キーがあると、親を消す前に子が残っていないか確認が必要
- 本番では論理削除（`deleted_at`）を使う設計もある

## 演習

- 問題: `samples/exercises/04_dml_questions.sql`
- 解答例: `samples/exercises/04_dml_answers.sql`

## 次の章

[07. インデックスと実行計画](07-indexes.md)
