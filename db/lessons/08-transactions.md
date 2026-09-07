# 08. トランザクション

## 目次

- [この章の目標](#この章の目標)
- [学習ポイント](#学習ポイント)
- [演習](#演習)
- [次の章](#次の章)

## この章の目標

- `BEGIN` / `COMMIT` / `ROLLBACK` を使える
- 原子性（オール・オア・ナッシング）の重要性を説明できる
- 分離レベル（READ COMMITTED など）の存在を知る

## 学習ポイント

注文確定＝「orders 挿入 + order_items 挿入」は、片方だけ成功すると壊れるためトランザクションでまとめる。

```sql
BEGIN;
-- 複数の DML
COMMIT;   -- 確定
-- または
ROLLBACK; -- 取り消し
```

PostgreSQL のデフォルト分離レベルは `READ COMMITTED` です。  
競合（ロストアップデート等）はアプリ設計と合わせて学びます。

## 演習

- 問題: `samples/exercises/06_transaction_questions.sql`
- 解答例: `samples/exercises/06_transaction_answers.sql`

## 次の章

[09. 正規化とモデリング](09-normalization.md)
