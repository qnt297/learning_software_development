# 11. Java SE / EE と Spring 概要

## 目次

- [この章の目標](#この章の目標)
- [なぜ大事か](#なぜ大事か)
- [学習ポイント](#学習ポイント)
  - [1. Java SE と Java EE（Jakarta EE）](#1-java-se-と-java-eejakarta-ee)
  - [2. アーキテクチャの粗い地図](#2-アーキテクチャの粗い地図)
  - [3. Spring Framework とは](#3-spring-framework-とは)
  - [4. Spring Boot とは](#4-spring-boot-とは)
  - [5. Spring Boot でよく出てくる部品（名前だけ）](#5-spring-boot-でよく出てくる部品名前だけ)
  - [6. Java EE（Jakarta EE）と Spring の比較（超概略）](#6-java-eejakarta-eeと-spring-の比較超概略)
  - [7. この教材からの次の一手](#7-この教材からの次の一手)
- [章末課題](#章末課題)
- [チェックリスト](#チェックリスト)
- [関連リンク](#関連リンク)

## この章の目標

- Java SE と Java EE（Jakarta EE）の違いを説明できる
- Spring Framework と Spring Boot の関係・役割を概観できる
- 「いつ素の Java で十分で、いつ Spring を使うか」の判断材料を持つ

## なぜ大事か

ここまでの章は、主に **Java SE**（言語と標準ライブラリ）で完結します。  
実務の Web／業務システムでは、その上に **エンタープライズ標準（Jakarta EE）** や、事実上のデファクト **Spring** が載ることがほとんどです。用語の地図がないと、求人票やドキュメントの言葉がつながりません。

> この章は **概要（地図）** です。Spring プロジェクトの本格ハンズオンは、ビルドツール（07 章）習得後に別途進める想定です。

## 学習ポイント

### 1. Java SE と Java EE（Jakarta EE）

| | **Java SE** | **Java EE → Jakarta EE** |
|--|-------------|---------------------------|
| 正式な意味 | Standard Edition。言語仕様＋標準 API（コレクション、I/O、並行、HTTP クライアント等） | 企業向け機能の仕様セット（Web、永続化、トランザクション、メッセージング等） |
| 誰が使うか | すべての Java 開発の土台 | アプリサーバ上の大規模／標準準拠システム、またはその仕様を実装した製品 |
| 実行のイメージ | `java` コマンドでクラス／jar を起動 | かつては App Server（WildFly, Payara 等）にデプロイ。現在は軽量実行も含む |
| 代表的な API 例 | `java.lang`, `java.util`, `java.nio`, `java.net.http` | Servlet, JPA, CDI, JAX-RS, Bean Validation など（現在は `jakarta.*` パッケージ） |

#### 名前の変遷（混乱しやすい点）

- 昔: **Java EE**（Java Platform, Enterprise Edition）— Oracle 配下
- 現在: **Jakarta EE** — Eclipse Foundation 配下。パッケージも `javax.*` から `jakarta.*` へ移行が進んだ
- 会話では今でも「Java EE」と言う人が多いが、新規は **Jakarta EE** を指す理解でよい

#### どう使い分けるか（感覚）

```text
学習・CLI・ライブラリ・アルゴリズム
  → Java SE で十分（本教材 01〜10）

Web API・DI・トランザクション・永続化を「エコシステムごと」使いたい
  → 多くの現場では Spring（次節）
  → 標準仕様ベースで進めたい現場では Jakarta EE 実装
```

**SE は常に土台**です。EE／Spring は、その上の「アプリケーション基盤」です。

### 2. アーキテクチャの粗い地図

典型的なサーバサイド Java アプリ:

```text
クライアント (Browser / 他サービス)
        ↓ HTTP
  Controller (Web / REST)
        ↓
  Service (ユースケース・トランザクション境界)
        ↓
  Repository / DAO
        ↓
     Database
```

07・09・10 章で触れた **レイヤ分離・疎結合・Repository** は、Spring を使っても同じ発想です。フレームワークは、この形を **アノテーションと自動設定で支えやすくする** 道具です。

### 3. Spring Framework とは

**Spring Framework** は、Java 向けの総合アプリケーションフレームワークです。中核は次です。

| 概念 | 意味 |
|------|------|
| **DI（Dependency Injection）** | 依存を自分で `new` せず、コンテナから注入してもらう（09 章の疎結合の実践） |
| **IoC コンテナ** | オブジェクトの生成・寿命・配線を管理する実行基盤 |
| **AOP** | ログ・トランザクション・セキュリティなど横断関心を本体ロジックから分離 |
| **エコシステム** | Spring MVC（Web）、Spring Data、Spring Security、Spring Batch など周辺プロジェクト |

イメージ:

```java
// 疑似コード（概念）
@Service
public class OrderService {
    private final OrderRepository repository;

    public OrderService(OrderRepository repository) { // コンストラクタインジェクション
        this.repository = repository;
    }
}
```

素の Java でも同じ設計はできますが、配線・設定・周辺機能を自前で揃えるコストが大きい、というのが Spring が選ばれる理由です。

### 4. Spring Boot とは

**Spring Boot** は、Spring Framework を **素早く・規約どおりに起動・構成** するための意見付き（opinionated）レイヤです。

| | Spring Framework | Spring Boot |
|--|------------------|-------------|
| 役割 | 機能の本体（DI、MVC 等） | その上の自動設定・スターター・実行容易化 |
| 設定 | XML／Java Config を自分で組み立てることが多い（歴史的経緯） | スターター依存＋`application.yml` で多くの既定が揃う |
| 起動 | 外部サーバに載せる構成もあった | 組み込み Tomcat 等で **実行可能 jar** が一般的 |
| キャッチフレーズ | 「柔軟な枠組み」 | 「本番志向のアプリをすぐ作る」 |

関係性:

```text
あなたのアプリ
    ↓ 使いやすい入口
Spring Boot（自動設定・starter・actuator 等）
    ↓ 中身として利用
Spring Framework（DI / MVC / TX など）
    ↓ 土台
Java SE（＋必要に応じて Jakarta 仕様の実装）
```

**「Boot か Framework か」ではなく、Boot が Framework を使いやすく包んでいる**、と覚えるのが正確です。

### 5. Spring Boot でよく出てくる部品（名前だけ）

| 名前 | 役割 |
|------|------|
| `spring-boot-starter-web` | REST / MVC アプリの定番セット |
| `spring-boot-starter-data-jpa` | JPA（Hibernate 実装が一般的）での永続化 |
| `application.yml` / `.properties` | ポート、DB 接続などの設定 |
| `@SpringBootApplication` | 起動クラスの入口アノテーション |
| `@RestController` / `@Service` / `@Repository` | レイヤの役割宣言（02 章のアノテーションの発展） |
| Spring Data | リポジトリインタフェースから実装を生成しやすくする |
| Actuator | ヘルスチェック等の運用エンドポイント |

DB 接続や SQL の基礎は、別トラックの [データベース学習ロードマップ](../../db/README.md) で学べます。Spring Data は「SQL／モデルの理解があるほど安全に使える」道具です。

### 6. Java EE（Jakarta EE）と Spring の比較（超概略）

| 観点 | Jakarta EE | Spring |
|------|------------|--------|
| 性格 | 標準仕様＋準拠実装 | デファクトのオープンソース枠組み |
| 学習・求人 | 仕様準拠案件、アプリサーバ系 | 国内・海外とも求人が非常に多い |
| 進め方 | 仕様を読んで実装を選ぶ | ドキュメントとスターターで早く形になる |
| 共存 | 一部 API（Servlet, JPA Validation 等）は Spring からも利用 | JPA 実装に Hibernate を使う、など仕様実装を内部利用することも多い |

新規学習で「まずサーバサイド Java に触る」なら、多くの場合 **Spring Boot から入る** のが現実的です。標準そのものを深く知る必要があるチームでは Jakarta EE を並行学習します。

### 7. この教材からの次の一手

1. 07 章の Maven / Gradle で空プロジェクトを作る
2. [Spring Initializr](https://start.spring.io/) で `Web` 付きの Boot プロジェクトを生成する
3. `@RestController` で Hello API を1本返す
4. [db ロードマップ](../../db/README.md) で SQL を学び、その後に Spring Data JPA や JDBC を接続する
5. 09 章の DI／Strategy／Repository が、Boot の中でどう表れるか対応づける

## 章末課題

1. Java SE / Jakarta EE / Spring Framework / Spring Boot を、それぞれ一文で説明する
2. 10 章のタスク管理 CLI を「もし Web API 化するなら」どのレイヤが Controller / Service / Repository になるか図示する
3. Spring Initializr でプロジェクトを作り、`GET /hello` が JSON または文字列を返すところまで進める（任意）

## チェックリスト

- [ ] SE が土台で、EE／Spring はその上のアプリ基盤だと説明できる
- [ ] Java EE と Jakarta EE の関係を説明できる
- [ ] Spring Framework と Spring Boot の上下関係を説明できる
- [ ] DI が 09 章の疎結合とどうつながるか一言で言える

## 関連リンク

- 次: [12. JDBC とコネクションプール](12-jdbc-and-pooling.md)
- 前の章: [10. ミニアプリケーション](10-mini-app.md)
- DB: [データベース学習ロードマップ](../../db/README.md)
- 設計: [09. 設計の基礎](09-design.md)
