# 12. JDBC とコネクションプール

## 目次

- [この章の目標](#この章の目標)
- [なぜ大事か](#なぜ大事か)
- [学習ポイント](#学習ポイント)
  - [1. JDBC とは](#1-jdbc-とは)
  - [2. 基本的な流れ](#2-基本的な流れ)
  - [3. 更新系とトランザクション](#3-更新系とトランザクション)
  - [4. SQL インジェクション対策](#4-sql-インジェクション対策)
  - [5. コネクションプールとは](#5-コネクションプールとは)
  - [6. Spring との関係（予告）](#6-spring-との関係予告)
  - [7. 依存の例（Maven Central）](#7-依存の例maven-central)
- [サンプルについて](#サンプルについて)
- [章末課題](#章末課題)
- [チェックリスト](#チェックリスト)
- [関連リンク](#関連リンク)

## この章の目標

- JDBC が「Java から DB へ話す標準 API」であることを説明できる
- `DriverManager` / `Connection` / `PreparedStatement` / `ResultSet` の役割を追える
- コネクションプールが必要な理由と、HikariCP などの位置づけを説明できる
- SQL インジェクションを避ける書き方（プレースホルダ）を実践できる

## なぜ大事か

[db ロードマップ](../../db/README.md) で学んだ SQL を、Java アプリから実行する橋が **JDBC** です。  
Spring Data JPA などを使っても、内部では JDBC（または同等の仕組み）に落ちます。土台を知っていると、障害時のログや性能問題の切り分けが速くなります。

## 学習ポイント

### 1. JDBC とは

**JDBC（Java Database Connectivity）** は、Java SE に含まれる DB アクセス用の API です。  
実際の通信は、各 DB の **JDBC ドライバ**（例: PostgreSQL の `org.postgresql:postgresql`）が担います。ドライバは [Maven Central](07-project-structure.md) から取得します。

```text
アプリコード
  → JDBC API (java.sql.*)
    → JDBC ドライバ (PostgreSQL 等)
      → データベース
```

### 2. 基本的な流れ

```java
String url = "jdbc:postgresql://localhost:5432/learning_shop";
String user = "postgres";
String password = "secret";

try (Connection conn = DriverManager.getConnection(url, user, password);
     PreparedStatement ps = conn.prepareStatement(
             "SELECT id, name, email FROM customers WHERE id = ?")) {
    ps.setLong(1, 1L);
    try (ResultSet rs = ps.executeQuery()) {
        while (rs.next()) {
            System.out.println(rs.getLong("id") + " " + rs.getString("name"));
        }
    }
}
```

| 型 | 役割 |
|----|------|
| `Connection` | DB セッション。トランザクションの境界にもなる |
| `PreparedStatement` | プレースホルダ付き SQL。再利用・安全性で推奨 |
| `Statement` | 文字列連結 SQL。学習以外では避けることが多い |
| `ResultSet` | 検索結果のカーソル |

`try-with-resources` で閉じる（05 章）のが必須です。閉じ忘れると接続リークになります。

### 3. 更新系とトランザクション

```java
conn.setAutoCommit(false);
try {
    // 複数の PreparedStatement を実行
    conn.commit();
} catch (SQLException e) {
    conn.rollback();
    throw e;
}
```

注文ヘッダ＋明細のように、複数 SQL を原子的に扱うときはアプリ側でもトランザクションを明示します（db 08 章と対応）。

### 4. SQL インジェクション対策

```java
// 危険: 入力を文字列連結
String sql = "SELECT * FROM customers WHERE email = '" + email + "'";

// 安全: プレースホルダ
PreparedStatement ps = conn.prepareStatement(
        "SELECT * FROM customers WHERE email = ?");
ps.setString(1, email);
```

ユーザー入力を SQL 文に直接埋め込まない、が鉄則です。

### 5. コネクションプールとは

`DriverManager.getConnection` は、その都度 TCP 接続の確立や認証が走り、**高頻度アクセスでは遅い・重い** です。  
**コネクションプール** は、あらかじめ接続を一定数作っておき、借りて・返して再利用する仕組みです。

```text
リクエスト1 → プールから借用 → SQL → 返却
リクエスト2 → プールから借用 → SQL → 返却
```

| よく使う実装 | 特徴 |
|--------------|------|
| **HikariCP** | 高速・軽量。Spring Boot のデフォルト |
| Tomcat JDBC Pool / DBCP | 歴史的に使われることも |

#### 設定で意識すること（概念）

| 項目 | 意味 |
|------|------|
| 最大プールサイズ | 同時に使える接続の上限（DB の `max_connections` と整合） |
| 最小アイドル | 待機中に維持する接続数 |
| タイムアウト | 借りられない／アイドル過長のときの打ち切り |
| 接続検証 | 壊れた接続を貸し出さない |

プールは「速くする魔法」ではなく、**リソース管理** です。サイズを無闇に大きくすると DB を圧迫します。

### 6. Spring との関係（予告）

- Spring の `JdbcTemplate` … JDBC のボイラープレートを減らす薄いラッパ
- Spring Data JPA … さらに抽象化（内部で JDBC／Hibernate）
- Boot はデータソースに HikariCP を自動設定することが多い

まずは「JDBC の流れ」と「プールが何を再利用するか」を押さえてから、抽象化レイヤに進むと迷子になりにくいです。

### 7. 依存の例（Maven Central）

```xml
<dependency>
  <groupId>org.postgresql</groupId>
  <artifactId>postgresql</artifactId>
  <version>42.7.4</version>
</dependency>
<dependency>
  <groupId>com.zaxxer</groupId>
  <artifactId>HikariCP</artifactId>
  <version>5.1.0</version>
</dependency>
```

## サンプルについて

この章は概念が中心です。動かすときは:

1. [db/samples/shop](../../db/samples/shop) のスキーマを PostgreSQL に流す
2. Maven プロジェクトに上記依存を追加する
3. 上記の `PreparedStatement` 例で `customers` を読む

## 章末課題

1. `customers` を全件表示する JDBC コードを書く（try-with-resources 必須）
2. 名前の一部で検索するとき、文字列連結と `PreparedStatement` の差を説明する
3. プール無しで接続を毎回開閉する場合の欠点を、レイテンシとリソースの両面で書く
4. HikariCP の最大プールサイズを、想定同時リクエストと DB 上限の関係で決める思考実験をする

## チェックリスト

- [ ] JDBC の役割とドライバの関係を説明できる
- [ ] `PreparedStatement` を選ぶ理由を説明できる
- [ ] 接続を必ず閉じる／プールへ返却する重要性を説明できる
- [ ] コネクションプールが再利用する対象を説明できる

## 関連リンク

- 前: [11. Java SE / EE と Spring](11-java-se-ee-and-spring.md)
- DB: [データベース学習ロードマップ](../../db/README.md)
- 依存取得: [07. Maven Central](07-project-structure.md)
