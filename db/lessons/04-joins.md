# 04. JOIN

## 目次

- [この章の目標](#この章の目標)
- [学習ポイント](#学習ポイント)
- [演習](#演習)
- [次の章](#次の章)

## この章の目標

- INNER / LEFT JOIN の違いを説明し、使える
- 結合条件を `ON` に書き、意図しないデカルト積を避ける

## 学習ポイント

| 種類 | 結果 |
|------|------|
| `INNER JOIN` | 両方にマッチする行だけ |
| `LEFT JOIN` | 左表はすべて。右が無ければ NULL |
| `RIGHT JOIN` | 右基準（LEFT に書き換えて考えることが多い） |
| `FULL OUTER JOIN` | どちらかにあれば残す（PostgreSQL で利用可） |

```sql
SELECT o.id, c.name, o.ordered_at
FROM orders o
INNER JOIN customers c ON c.id = o.customer_id;
```

## 演習

- 問題: `samples/exercises/02_join_questions.sql`
- 解答例: `samples/exercises/02_join_answers.sql`

## 次の章

[05. 集計とサブクエリ](05-aggregation.md)
