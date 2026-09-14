# 06. ラムダと Stream API

## 目次

- [この章の目標](#この章の目標)
- [なぜ大事か](#なぜ大事か)
- [学習ポイント](#学習ポイント)
  - [1. ラムダ式](#1-ラムダ式)
  - [2. 構文の中で何が起きているか](#2-構文の中で何が起きているか)
  - [3. メソッド参照](#3-メソッド参照)
  - [4. Stream の典型パイプライン](#4-stream-の典型パイプライン)
  - [5. よく使う終端操作](#5-よく使う終端操作)
  - [6. 注意点](#6-注意点)
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

ラムダは「メソッドの実装だけを、その場に書く」記法です。名前付きクラスを作らず、**関数型インタフェース（メソッドが実質1つ）の実装**として渡します。

```java
names.forEach(name -> System.out.println(name));
```

### 2. 構文の中で何が起きているか

#### 構文の分解

```text
( 引数リスト )  ->  { 本体 }
     ↑               ↑
  メソッドの仮引数    メソッドの中身
```

| 部品 | 役割 | 例 |
|------|------|----|
| 左辺 | 呼び出されたときに受け取る値 | `name` / `(a, b)` |
| `->` | 「左を受け取ったら、右を実行する」という接続 | |
| 右辺 | 実行する処理。値を返すなら `return` 相当 | `System.out.println(name)` |

1引数・本体が1文のとき、よくこう省略されます。

```java
name -> System.out.println(name)
```

省略を戻すと、中身はこうです。

```java
(String name) -> {
    System.out.println(name);
}
```

対応表:

| 省略形 | 省略しない形 |
|--------|----------------|
| `n -> n.length() >= 3` | `(String n) -> { return n.length() >= 3; }` |
| `(a, b) -> a + b` | `(int a, int b) -> { return a + b; }` |
| `() -> "ok"` | `() -> { return "ok"; }` |

- 引数が **1つ** なら `()` を省略できる
- 本体が **式1つ** なら `{ }` と `return` を省略できる（その式の値が戻り値）
- 引数の型は、渡す先（`Consumer<String>` など）からコンパイラが推論する

#### 匿名クラスが縮んだもの、という見方

`forEach` が欲しいのは「文字列を1つ受け取って何かするオブジェクト」です。インタフェースはこれです。

```java
@FunctionalInterface
public interface Consumer<T> {
    void accept(T t);  // 実装すべきメソッドは実質これだけ（SAM）
}
```

ラムダ以前は、匿名クラスでこう書いていました。

```java
names.forEach(new Consumer<String>() {
    @Override
    public void accept(String name) {
        System.out.println(name);
    }
});
```

ラムダは、この **`accept` の引数と本体だけ残したもの** です。

```java
names.forEach(name -> System.out.println(name));
```

対応関係:

```text
匿名クラスの accept(String name)  { System.out.println(name); }
                 │                    │
                 ▼                    ▼
ラムダの         name            ->  System.out.println(name)
```

クラス名・`new`・`@Override`・メソッド名 `accept` は、型が `Consumer` だと分かっているので書かなくてよい、という仕組みです。

#### `forEach` の中での作用

`List.forEach` のイメージ実装です（簡略）。

```java
public void forEach(Consumer<String> action) {
    for (String element : this) {
        action.accept(element);  // ここでラムダ本体が呼ばれる
    }
}
```

実行の流れ:

```text
names = ["Ada", "Alan"]
forEach(name -> System.out.println(name))

  1周目: action.accept("Ada")
          → ラムダ本体: System.out.println("Ada")
  2周目: action.accept("Alan")
          → ラムダ本体: System.out.println("Alan")
```

**渡しているのは「処理そのもの」** です。`forEach` が要素を取り出し、渡した処理に順番に渡します。呼び出し側は「各要素に何をするか」だけを書きます。

#### `filter` の中での作用

```java
names.stream()
     .filter(n -> n.length() >= 3)
```

`filter` が欲しいのは `Predicate<String>`（`boolean test(String n)`）です。

```text
n -> n.length() >= 3
  ≡  boolean test(String n) { return n.length() >= 3; }
```

Stream は各要素について `test` を呼び、`true` のものだけ後段へ残します。

```text
"Ada"  → test → true  → 残る
"Al"   → test → false → 捨てる
```

`map(s -> s.toUpperCase())` なら `Function<String, String>` の `apply` に相当し、入ってきた値を別の値に変換します。

#### よく使う関数型インタフェース

| インタフェース | メソッド | ラムダの意味 | 典型用途 |
|----------------|----------|--------------|----------|
| `Consumer<T>` | `void accept(T)` | 受け取って使う（返さない） | `forEach` |
| `Predicate<T>` | `boolean test(T)` | 条件判定 | `filter` |
| `Function<T,R>` | `R apply(T)` | 変換 | `map` |
| `Supplier<T>` | `T get()` | 引数なしで作る | 遅延生成 |
| `Runnable` | `void run()` | 引数も戻りも無し | スレッド |

どれも **抽象メソッドが1つ（SAM）** なので、ラムダで実装できます。これが `@FunctionalInterface` です（02 章）。

#### 外側の変数（キャプチャ）

ラムダは、外側の **実質 final** なローカル変数を読めます。

```java
String prefix = "Hello, ";
names.forEach(name -> System.out.println(prefix + name));
```

`prefix` をラムダのあとで書き換えるとコンパイルエラーになります。ループ内で外側の `count++` をラムダから行うのも同様に制限されます（Stream に副作用を載せない、という注意へつながります）。

### 3. メソッド参照

```java
names.forEach(System.out::println);
```

### 4. Stream の典型パイプライン

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

### 5. よく使う終端操作

- `toList()` / `collect(Collectors.toList())`
- `count()`
- `findFirst()`
- `anyMatch` / `allMatch`
- `reduce`

### 6. 注意点

- 副作用（ループ内で外部変数を書き換える等）を Stream 内に詰め込まない
- 並列 Stream（`parallelStream`）は安易に使わない
- `null` 混入に弱いので、上流で除外する

## サンプル一覧

| ファイル | 内容 |
|----------|------|
| `LambdaBasics.java` | ラムダとメソッド参照 |
| `LambdaAnatomyDemo.java` | 匿名クラス ↔ ラムダの対応と、forEach/filter での作用 |
| `StreamPipelineDemo.java` | filter/map/集計 |

## 章末課題

1. 商品リストから税込価格が1000円以上のものだけ抽出し、名前のリストを作る
2. 文字列リストの文字数合計を `mapToInt` + `sum` で求める
3. 同じ処理を従来の for ループでも書き、可読性を比較する
4. `new Consumer<String>() { ... }` で書いた `forEach` をラムダに書き換え、引数と本体が匿名クラスのどこに対応するかコメントする

## チェックリスト

- [ ] ラムダの `(args) -> body` を、匿名クラスのどの部分に相当するか説明できる
- [ ] `forEach` がラムダを `accept` 相当で呼ぶ流れを説明できる
- [ ] filter と map の違いを説明できる
- [ ] 終端操作がないと中間操作が実行されないことを知っている

## 次の章

[07. パッケージとプロジェクト構成](07-project-structure.md)
