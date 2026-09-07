# 03. SELECT 基礎

## 目次

- [この章の目標](#この章の目標)
- [学習ポイント](#学習ポイント)
- [演習](#演習)
- [次の章](#次の章)

## この章の目標

- `WHERE` / `ORDER BY` / `LIMIT` / `OFFSET` を使える
- 比較・NULL・パターンマッチ（`LIKE`）の基本を扱う

## 学習ポイント

```sql
SELECT column_list
FROM table_name
WHERE condition
ORDER BY column ASC|DESC
LIMIT n OFFSET m;
```

注意:

- `=` で NULL は比較できない → `IS NULL` / `IS NOT NULL`
- 文字列比較は照合順序（collation）の影響を受ける
- `SELECT *` は探索に便利だが、本番クエリでは列を明示することが多い

## 演習

- 問題: `samples/exercises/01_select_questions.sql`
- 解答例: `samples/exercises/01_select_answers.sql`

## 次の章

[04. JOIN](04-joins.md)
