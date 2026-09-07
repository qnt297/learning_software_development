# 01. RDB と SQL の全体像

## 目次

- [この章の目標](#この章の目標)
- [なぜ大事か](#なぜ大事か)
- [学習ポイント](#学習ポイント)
  - [用語](#用語)
  - [SQL の分類（ざっくり）](#sql-の分類ざっくり)
  - [題材の関係](#題材の関係)
- [サンプル](#サンプル)
- [章末課題](#章末課題)
- [次の章](#次の章)

## この章の目標

- RDB（リレーショナルデータベース）の基本用語を説明できる
- SQL の役割（DDL / DML / DCL / TCL）の大枠を知る
- 題材スキーマ `learning_shop` の表の関係を追える

## なぜ大事か

アプリの状態の多くは DB にあります。SQL が読めないと、障害調査も機能追加も「アプリ側の当て推量」になりがちです。

## 学習ポイント

### 用語

| 用語 | 意味 |
|------|------|
| テーブル（表） | 同じ形の行の集合 |
| 行（レコード） | 1件のデータ |
| 列（カラム） | 属性 |
| 主キー（PK） | 行を一意に識別する列（または列の組） |
| 外部キー（FK） | 他表の PK を参照し、関係を表す |
| SQL | 定義・操作・制御のための言語 |

### SQL の分類（ざっくり）

| 分類 | 例 | 用途 |
|------|----|------|
| DDL | `CREATE`, `ALTER`, `DROP` | 構造の定義 |
| DML | `SELECT`, `INSERT`, `UPDATE`, `DELETE` | データの操作 |
| TCL | `BEGIN`, `COMMIT`, `ROLLBACK` | トランザクション |
| DCL | `GRANT`, `REVOKE` | 権限（運用寄り） |

### 題材の関係

```text
categories 1 ── * products
customers  1 ── * orders
orders     1 ── * order_items
products   1 ── * order_items
```

## サンプル

- `samples/shop/00_schema.sql` … 表定義
- `samples/shop/01_seed.sql` … 初期データ

まず両方を実行し、次を試してください。

```sql
SELECT * FROM customers;
SELECT * FROM products;
```

## 章末課題

1. `orders` と `customers` がどうつながるか、自分の言葉で説明する
2. 「主キーが無い表」で困ることの例を1つ挙げる

## 次の章

[02. DDL](02-ddl.md)
