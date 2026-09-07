# 07. パッケージとプロジェクト構成

## 目次

- [この章の目標](#この章の目標)
- [なぜ大事か](#なぜ大事か)
- [学習ポイント](#学習ポイント)
  - [1. `package` と `import` の違い（詳細）](#1-package-と-import-の違い詳細)
  - [2. 推奨ディレクトリ（ソース）](#2-推奨ディレクトリソース)
  - [3. 依存の向き](#3-依存の向き)
  - [4. ビルドツール（詳細）](#4-ビルドツール詳細)
- [サンプル一覧](#サンプル一覧)
- [章末課題](#章末課題)
- [チェックリスト](#チェックリスト)
- [次の章](#次の章)

## この章の目標

- `package` と `import` の違い・役割を正確に説明できる
- レイヤ（UI / ドメイン / インフラ）の分離イメージを持つ
- Maven / Gradle などビルドツールが何をしてくれるかを説明できる
- Maven Central の役割と GAV の読み方を説明できる
- Maven 的な標準ディレクトリ構成を説明できる

## なぜ大事か

単一ファイルのデモでは問題が起きませんが、機能が増えると「どこに何を置くか」で生産性が決まります。  
パッケージ設計はチーム開発の地図であり、ビルドツールは「再現可能な成果物」を作る工場です。

## 学習ポイント

### 1. `package` と `import` の違い（詳細）

この2つはよくセットで出てきますが、**役割がまったく違います**。

| | `package` | `import` |
|--|-----------|----------|
| 何をするか | 「このクラスが属する名前空間」を **宣言** する | 「他のクラスの短縮名」を使えるようにする **参照の省略** |
| 書く場所 | ソース先頭（ほぼ必須の自己定義） | 必要に応じて複数行 |
| ディレクトリ | パッケージ名とフォルダ構造が一致必須 | ディレクトリには直接関係しない |
| 無いとどうなるか | デフォルトパッケージ（実務では非推奨） | 毎回 FQCN（完全修飾名）で書く必要がある |

#### package … 自分の住所を名乗る

```java
package com.example.shop.domain;

public class Product {
    // ...
}
```

- ファイルは `.../com/example/shop/domain/Product.java` に置く
- クラスの正式名（FQCN）は `com.example.shop.domain.Product`
- 同じパッケージ内のクラスは、import なしで参照できる
- アクセス修飾子なし（パッケージプライベート）の可視範囲の単位にもなる

慣例は **逆ドメイン形式**: `com.company.product.layer`  
世界中で名前がぶつかりにくく、組織とレイヤが読み取れるためです。

#### import … 他人の住所を短縮して呼ぶ

```java
package com.example.shop.service;

import com.example.shop.domain.Product; // 短縮名 Product を使えるようにする
import java.util.ArrayList;
import java.util.List;

public class CartService {
    private final List<Product> items = new ArrayList<>();
}
```

`import` はクラスを「読み込む」わけではありません。コンパイル時に名前解決するための宣言です。  
実行時にクラスパスから読み込まれるのは、実際に参照されたクラスです。

同じことを import なしで書くと:

```java
private final java.util.List<com.example.shop.domain.Product> items =
        new java.util.ArrayList<>();
```

#### import の種類

```java
import java.util.List;           // 単一型
import java.util.*;              // オンデマンド（そのパッケージ直下の型）
import static java.lang.Math.PI; // static import（定数や static メソッド）
```

実務 Tip:

- `*` の乱用は、どの型を使っているか追いにくい。IDE が整理してくれるなら単一 import を基本に
- 同名クラス（例: `java.util.Date` と `java.sql.Date`）が衝突したら、片方は FQCN で書く
- `java.lang.*`（String, System など）は自動的に見えるので import 不要

#### よくある誤解

| 誤解 | 実際 |
|------|------|
| import すると依存ライブラリがダウンロードされる | しない。依存解決はビルドツールの仕事 |
| package はフォルダと違っても動く | 一致が必須。ズレるとコンパイルエラー |
| import が多いほど実行が重い | 実行時コストはほぼ無関係。名前解決の話 |

### 2. 推奨ディレクトリ（ソース）

```text
src/main/java/com/example/app/
├── App.java              # 起動
├── domain/               # 業務ルール・モデル
├── service/              # ユースケース
└── infra/                # ファイル・DB など外部接続
```

テストは対になる形で:

```text
src/test/java/com/example/app/
```

### 3. 依存の向き

```text
UI / CLI  →  service  →  domain
                ↓
              infra
```

**domain が infra に依存しない** のが理想です（詳細は 09 章）。

### 4. ビルドツール（詳細）

素の `javac` でも学べますが、実務の Java プロジェクトはほぼ必ずビルドツールを使います。

#### ビルドツールが担うこと

1. **依存関係の取得** … ライブラリ（例: JUnit）をリポジトリから解決
2. **コンパイル** … ソース → `.class`
3. **テスト実行** … テストを自動実行し、失敗ならビルド失敗
4. **パッケージング** … `jar` / `war` など成果物にまとめる
5. **再現性** … 誰が・いつ・どのマシンでも同じ手順でビルドできる
6. **品質ゲート** … カバレッジ、静的解析（08 章）を CI に載せる入口

`javac` だけだと、依存の版管理・クラスパス指定・テスト実行・成果物作成をすべて手作業で抱え込みます。

#### Maven

- 設定ファイル: `pom.xml`（Project Object Model）
- 「規約優先」が強い。ディレクトリを標準に合わせると設定が少なくて済む
- 中央リポジトリ（Maven Central）から依存を取得
- 代表コマンド:

| コマンド | 意味 |
|----------|------|
| `mvn compile` | 本番ソースをコンパイル |
| `mvn test` | テスト実行 |
| `mvn package` | jar などを作成 |
| `mvn clean` | 成果物削除 |

最小イメージ:

```xml
<project>
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.example</groupId>
  <artifactId>shop</artifactId>
  <version>0.1.0</version>
  <properties>
    <maven.compiler.release>17</maven.compiler.release>
  </properties>
</project>
```

向いているケース: 標準的な構成、社内で Maven が標準、プラグイン生態系を活かしたいとき。

#### Maven Central（中央リポジトリ）

**Maven Central** は、Java エコシステムで最も使われる **公開ライブラリの倉庫** です。  
Maven / Gradle は、`pom.xml` や `build.gradle` に書いた座標を解決するとき、既定でここ（やミラー）から成果物をダウンロードします。

| 項目 | 内容 |
|------|------|
| 公式の検索 UI | [https://central.sonatype.com/](https://central.sonatype.com/)（旧 search.maven.org 系） |
| 何があるか | オープンソースの jar、ソース jar、Javadoc、POM |
| 誰が使うか | ほぼすべての Java ビルド（直接または経由） |

##### ライブラリの座標（GAV）

Maven ではライブラリを次の3つで一意に指します。

```text
groupId    … 組織・プロジェクトの名前空間（例: org.postgresql）
artifactId … 成果物名（例: postgresql）
version    … 版（例: 42.7.4）
```

`pom.xml` での指定例:

```xml
<dependencies>
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
</dependencies>
```

Gradle では:

```gradle
repositories {
    mavenCentral()
}

dependencies {
    implementation 'org.postgresql:postgresql:42.7.4'
    implementation 'com.zaxxer:HikariCP:5.1.0'
}
```

##### 使い方（実務の流れ）

1. [Maven Central の検索](https://central.sonatype.com/) でライブラリ名を探す（例: `junit`, `postgresql`, `hikaricp`）
2. 必要な **groupId / artifactId / version** をコピーする
3. `pom.xml`（または Gradle）の `dependencies` に貼る
4. `mvn test` / `./gradlew build` で取得・コンパイルを確認する
5. ローカルキャッシュ（通常 `~/.m2/repository`）に保存され、次回以降は再利用される

##### 覚えておくこと

- **import ではダウンロードされない**（07 章前半）。取得するのはビルドツール＋リポジトリ
- 社内では Maven Central に加え、**社内 Nexus / Artifactory** を使うことがある
- 版はできるだけ明示する。意図しない最新取得は再現性を壊す
- ライセンスと既知の脆弱性（依存スキャン）も、導入判断に含める
- JDBC ドライバやコネクションプールも Central から取る（[12 章](12-jdbc-and-pooling.md)）

#### Gradle

- 設定ファイル: `build.gradle` または `build.gradle.kts`（Kotlin DSL）
- 柔軟なビルドスクリプト。大規模・Android・複合プロジェクトで強い
- インクリメンタルビルドやビルドキャッシュが得意
- 代表コマンド: `./gradlew build`, `./gradlew test`

最小イメージ（Groovy DSL）:

```gradle
plugins {
    id 'java'
}

java {
    toolchain {
        languageVersion = JavaLanguageVersion.of(17)
    }
}

repositories {
    mavenCentral()
}

dependencies {
    testImplementation 'org.junit.jupiter:junit-jupiter:5.10.2'
}
```

向いているケース: カスタムタスクが多い、マルチプロジェクト、Gradle が標準のチーム。

#### Maven と Gradle の比較

| 観点 | Maven | Gradle |
|------|-------|--------|
| 設定の書き方 | XML（宣言的） | Groovy/Kotlin（柔軟） |
| 学習曲線 | 規約を覚えれば素直 | 自由度が高い分、沼もありうる |
| 性能 | 十分速い | キャッシュ等が強いことが多い |
| 採用 | 企業 Java で依然多い | Android / 新規でも多い |

どちらも「依存・ビルド・テスト・成果物」という目的は同じです。チーム標準に合わせることが最優先です。

#### この教材との関係

サンプルは学習しやすさ優先で素の `javac` 構成です。  
10 章まで一通り動かしたら、同じミニアプリを Maven か Gradle に載せてみるのが次の一手です。

## サンプル一覧

| ファイル | 内容 |
|----------|------|
| `com/example/shop/domain/Product.java` | ドメインモデル |
| `com/example/shop/service/CartService.java` | ユースケース（import で domain を参照） |
| `com/example/shop/App.java` | 起動クラス |

## 章末課題

1. `domain` / `service` を分けた「ToDo」小さな構成を自作する
2. パッケージ図（箱と矢印）を紙または Markdown で描く
3. なぜ `domain` からファイル I/O を直接呼ばない方がよいか考える
4. 同じクラス参照を「import あり」と「FQCN のみ」の両方で書き、可読性を比較する
5. Maven か Gradle のどちらかで、空の Java プロジェクトを作り `test` まで通す（任意）
6. Maven Central で `HikariCP` を検索し、GAV をメモして `pom.xml` に書く練習をする

## チェックリスト

- [ ] `package` は住所の宣言、`import` は短縮名の宣言だと説明できる
- [ ] パッケージ宣言とディレクトリが一致している
- [ ] 起動クラスとドメインを分けられる
- [ ] ビルドツールが依存解決・テスト・成果物作成を担うと説明できる
- [ ] Maven Central と GAV（groupId/artifactId/version）を説明できる
- [ ] Maven の `src/main/java` 慣習を説明できる

## 次の章

[08. ユニットテスト入門](08-testing.md)
