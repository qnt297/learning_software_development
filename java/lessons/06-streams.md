# 06. ラムダと Stream API

## 目次

- [この章の目標](#この章の目標)
- [なぜ大事か](#なぜ大事か)
- [学習ポイント](#学習ポイント)
  - [1. ラムダ式](#1-ラムダ式)
  - [2. メソッド参照](#2-メソッド参照)
  - [3. Stream の典型パイプライン](#3-stream-の典型パイプライン)
  - [4. よく使う終端操作](#4-よく使う終端操作)
  - [5. 注意点](#5-注意点)
- [サンプル一覧](#サンプル一覧)
- [章末課題](#章末課題)
- [チェックリスト](#チェックリスト)
- [次の章](#次の章)

## この章の目標

- ラムダ式とメソッド参照を読める・書ける
- `filter` / `map` / `collect` でコレクションを変換できる
- 「命令的ループ」と「宣言的 Stream」を使い分けられる

## なぜ大事か

データ加工はアプリの中心処理です。Stream を使うと、意図（何をしたいか）がコードに残りやすく、テストもしやすくなります。  
ただし、単純なループの方が読みやすい場面もあります。道具として選ぶ力が重要です。

## 学習ポイント

### 1. ラムダ式

```java
names.forEach(name -> System.out.println(name));
```

### 2. メソッド参照

```java
names.forEach(System.out::println);
```

### 3. Stream の典型パイプライン

```java
List<String> result = names.stream()
        .filter(n -> n.length() >= 3)
        .map(String::toUpperCase)
        .sorted()
        .toList(); // Java 16+
```

流れ:

1. ソース（`stream()`）
2. 中間操作（遅延評価）
3. 終端操作（ここで計算が走る）

### 4. よく使う終端操作

- `toList()` / `collect(Collectors.toList())`
- `count()`
- `findFirst()`
- `anyMatch` / `allMatch`
- `reduce`

### 5. 注意点

- 副作用（ループ内で外部変数を書き換える等）を Stream 内に詰め込まない
- 並列 Stream（`parallelStream`）は安易に使わない
- `null` 混入に弱いので、上流で除外する

## サンプル一覧

| ファイル | 内容 |
|----------|------|
| `LambdaBasics.java` | ラムダとメソッド参照 |
| `StreamPipelineDemo.java` | filter/map/集計 |

## 章末課題

1. 商品リストから税込価格が1000円以上のものだけ抽出し、名前のリストを作る
2. 文字列リストの文字数合計を `mapToInt` + `sum` で求める
3. 同じ処理を従来の for ループでも書き、可読性を比較する

## チェックリスト

- [ ] ラムダの `(args) -> body` を読める
- [ ] filter と map の違いを説明できる
- [ ] 終端操作がないと中間操作が実行されないことを知っている

## 次の章

[07. パッケージとプロジェクト構成](07-project-structure.md)
