# 03. コレクションとジェネリクス

## 目次

- [この章の目標](#この章の目標)
- [なぜ大事か](#なぜ大事か)
- [学習ポイント](#学習ポイント)
  - [1. 配列と多次元配列](#1-配列と多次元配列)
  - [2. 主なコレクション](#2-主なコレクション)
  - [3. 要素の取り出し方：ベストプラクティス](#3-要素の取り出し方ベストプラクティス)
  - [4. ジェネリクス](#4-ジェネリクス)
  - [5. 反復処理と不変コレクション](#5-反復処理と不変コレクション)
- [サンプル一覧](#サンプル一覧)
- [章末課題](#章末課題)
- [チェックリスト](#チェックリスト)
- [次の章](#次の章)

## この章の目標

- 配列（一次元・多次元）とコレクションの違いを説明できる
- `List` / `Set` / `Map` の使い分けができる
- 各データ構造からの要素取り出しのベストプラクティスを実践できる
- ジェネリクスで型安全なコレクションを扱える

## なぜ大事か

アプリの大半は「複数のデータを集めて加工する」処理です。  
配列だけで押し通すと、追加・削除・検索のたびに自前実装が増え、バグの温床になります。一方、配列が最適な場面（固定長・性能要件・行列計算など）もあります。用途に応じて選ぶ力が必要です。

## 学習ポイント

### 1. 配列と多次元配列

#### 一次元配列

```java
int[] scores = {80, 90, 75};
System.out.println(scores[0]);      // 先頭
System.out.println(scores.length);  // 要素数（メソッドではない）
```

- 長さは生成時に固定（途中で伸ばせない）
- インデックスは `0` 始まり。範囲外は `ArrayIndexOutOfBoundsException`

#### 多次元配列（ジャグ配列）

Java の「二次元配列」は、正確には **配列の配列** です。行ごとに列数が違っても構いません。

```java
// 3行 x 2列の矩形
int[][] matrix = {
        {1, 2},
        {3, 4},
        {5, 6}
};

int value = matrix[1][0]; // 2行目・1列目 → 3

// 行ごとに長さが違う（ジャグ配列）
int[][] jagged = new int[3][];
jagged[0] = new int[] {1};
jagged[1] = new int[] {2, 3};
jagged[2] = new int[] {4, 5, 6};
```

ネストして回す典型:

```java
for (int row = 0; row < matrix.length; row++) {
    for (int col = 0; col < matrix[row].length; col++) {
        System.out.print(matrix[row][col] + " ");
    }
    System.out.println();
}
```

#### 配列を選ぶとき / 選ばないとき

| 向く | 向かない |
|------|----------|
| 長さがほぼ固定 | 頻繁に追加・削除 |
| インデックスで高速アクセスしたい | 「キーで探す」「重複を排除」が主目的 |
| 画像・行列など規則的な格子データ | 可変長の業務一覧 |

可変長の業務データは、多くの場合 `List` などのコレクションが適切です。

### 2. 主なコレクション

| 型 | 特徴 | 向いている用途 |
|----|------|----------------|
| `List` | 順序あり・重複可 | 履歴、一覧 |
| `Set` | 重複なし | タグ、一意な ID |
| `Map` | キー→値 | 辞書、インデックス |

実装クラスの定番:

- `ArrayList` … 高速なランダムアクセス
- `LinkedList` … 端での追加削除は得意だが、安易に選ばない（多くの場合 ArrayList で十分）
- `HashSet` … 高速な存在確認
- `LinkedHashSet` … 挿入順を保つ Set
- `HashMap` … キー検索
- `LinkedHashMap` … 挿入順を保つ Map
- `TreeMap` / `TreeSet` … ソート順が必要なとき

### 3. 要素の取り出し方：ベストプラクティス

#### 配列

```java
// 推奨: 拡張 for（インデックス不要なとき）
for (int score : scores) {
    System.out.println(score);
}

// インデックスが必要なときだけ通常 for
for (int i = 0; i < scores.length; i++) {
    System.out.println(i + ": " + scores[i]);
}
```

- 取り出し前に `length` と範囲を意識する
- 多次元は `matrix[row].length` を行ごとに見る（矩形前提で固定しない）

#### List

```java
List<String> names = List.of("Ada", "Alan", "Grace");

// 推奨1: 拡張 for（読み取り）
for (String name : names) { /* ... */ }

// 推奨2: Stream（変換・抽出が主目的）
names.stream().filter(n -> n.startsWith("A")).toList();

// インデックスアクセスはランダムアクセス用途に
String first = names.get(0);

// ループ中に要素削除するなら Iterator / removeIf
names.removeIf(n -> n.length() < 3);
```

避けること:

- ループ内で `list.get(i)` を `LinkedList` に対して大量に回す（遅い）
- 拡張 for の最中に `list.remove(...)` する（`ConcurrentModificationException`）

#### Set

```java
Set<String> tags = Set.of("java", "backend");

// 順序に依存しない反復
for (String tag : tags) { /* ... */ }

// 「含むか」が主目的
if (tags.contains("java")) { /* ... */ }
```

- Set に「何番目」はない（`LinkedHashSet` でも「順番付きの集合」であり List の代替ではない）
- 取り出しの主オペレーションは `contains` / 反復。インデックスアクセスが必要なら List を使う

#### Map

```java
Map<String, Integer> scores = Map.of("Alice", 90, "Bob", 75);

// キーが分かっているとき
Integer alice = scores.get("Alice");           // なければ null
int bob = scores.getOrDefault("Bob", 0);       // なければ既定値
int carol = scores.getOrDefault("Carol", 0);

// エントリを全部見る（推奨）
for (Map.Entry<String, Integer> e : scores.entrySet()) {
    System.out.println(e.getKey() + "=" + e.getValue());
}

// Java 8+
scores.forEach((name, score) -> System.out.println(name + "=" + score));
```

ベストプラクティス:

| やりたいこと | 推奨 |
|--------------|------|
| キーで1件取得 | `get` / `getOrDefault` / `Optional` 化 |
| 全件処理 | `entrySet()` または `forEach` |
| キーだけ / 値だけ | `keySet()` / `values()`（値の変更用途に注意） |
| なければ作る | `computeIfAbsent` |
| null キー・null 値 | 実装依存。基本避ける |

`map.get(key)` の結果を即 unbox（`int x = map.get(key);`）すると、キー欠落時に NPE になります。`getOrDefault` や存在確認を使います。

### 4. ジェネリクス

```java
List<String> names = new ArrayList<>();
Map<String, Integer> scores = new HashMap<>();
```

`<String>` により、誤って `Integer` を入れるコンパイルエラーを防げます。  
生の型（`List` だけ）はレガシー互換用で、新規コードでは使いません。

### 5. 反復処理と不変コレクション

```java
for (String name : names) {
    System.out.println(name);
}

List<String> fixed = List.of("a", "b"); // 変更不可
```

意図しない変更を防ぐなら、返すときに `List.copyOf` や `List.of` で不変化します。

## サンプル一覧

| ファイル | 内容 |
|----------|------|
| `ArrayDemo.java` | 一次元・多次元配列と取り出し |
| `ListDemo.java` | List の基本操作 |
| `SetAndMapDemo.java` | Set / Map の使い分け |
| `GenericBox.java` / `GenericDemo.java` | 自作ジェネリッククラス |

## 章末課題

1. 学生名→点数の `Map` を作り、平均点と最高点を求める（`entrySet` を使う）
2. 単語リストから重複を除いた `Set` を作り、件数を表示する
3. `Pair<A, B>` ジェネリッククラスを自作する
4. 3x3 の二次元配列で九九の一部を作り、拡張 for とインデックス for の両方で表示する

## チェックリスト

- [ ] 配列と List の使い分けを説明できる
- [ ] 多次元配列（配列の配列）として要素にアクセスできる
- [ ] List / Set / Map それぞれで「推奨の取り出し方」を選べる
- [ ] 生の型を避け、`<T>` を付けられる

## 次の章

[04. 例外処理](04-exceptions.md)
