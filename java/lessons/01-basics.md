# 01. Java 基礎文法

## 目次

- [この章の目標](#この章の目標)
- [なぜ大事か](#なぜ大事か)
- [学習ポイント](#学習ポイント)
  - [1. プログラムの骨格](#1-プログラムの骨格)
  - [2. 型と変数](#2-型と変数)
  - [3. 制御構文（if / for / switch / break）](#3-制御構文if--for--switch--break)
  - [4. メソッド](#4-メソッド)
  - [5. アクセス修飾子（public / protected / private）](#5-アクセス修飾子public-protected-private)
- [サンプル一覧](#サンプル一覧)
- [章末課題](#章末課題)
- [チェックリスト](#チェックリスト)
- [次の章](#次の章)

## この章の目標

- Java プログラムの最小構成（クラス・`main`）を理解する（仕組みの詳細は [00 章](00-runtime.md)）
- 変数・型・演算・制御構文・メソッドを使える
- `if` / `for` / `switch` / `break` / `continue` の記法を自分で書ける
- `public` / `protected` / `private`（とパッケージプライベート）の違いと実務での使い分けを説明できる
- コンソール入力と出力で対話的な小さなプログラムを書ける

## なぜ大事か

ソフトウェア開発では「仕様をコードに落とす」作業が中心です。  
基礎文法は、その翻訳の語彙です。語彙が曖昧なまま進むと、後の OOP や設計の話が空転します。

特にアクセス修飾子は、「誰にどこまで見せるか」という **境界の設計** です。チーム開発では、公開面が広いほど変更の影響範囲が広がり、バグとレビューコストが増えます。

## 学習ポイント

### 1. プログラムの骨格

```java
public class HelloWorld {
    public static void main(String[] args) {
        System.out.println("Hello, Java");
    }
}
```

- `public class` … 公開クラス。ファイル名とクラス名を一致させる
- `main` … JVM が最初に呼ぶエントリポイント（なぜこの形かは [00 章](00-runtime.md)）
- `String[] args` … コマンドライン引数

### 2. 型と変数

| 種類 | 例 | 用途 |
|------|----|------|
| 整数 | `int`, `long` | 個数・ID |
| 小数 | `double` | 金額以外の計測値など |
| 真偽 | `boolean` | 条件 |
| 文字/文字列 | `char`, `String` | 表示・入力 |

実務では、金額は `BigDecimal`、ID は意味のある型（後の章）を検討します。まずは `int` / `String` / `boolean` を確実に。

### 3. 制御構文（if / for / switch / break）

条件と繰り返しは、仕様をコードに落とすときの骨格です。サンプルは `ControlFlow.java` にまとめてあります。

#### if / else if / else

条件が真（`true`）のときだけブロックを実行します。上から順に評価し、最初に当たった枝だけが走ります。

```java
int score = 82;
if (score >= 80) {
    System.out.println("優");
} else if (score >= 60) {
    System.out.println("良");
} else {
    System.out.println("不可");
}
```

- 条件は `boolean`。`if (score)` のように数値を直接は書けない（C 言語との違い）
- 文が1行でも `{ }` を付ける習慣が、後のバグを減らす
- ネストが深いなら、メソッド分割や `switch`、早期 return を検討する

#### for / 拡張 for / while

```java
// カウンタ付き for: 初期化; 継続条件; 更新
for (int i = 1; i <= 3; i++) {
    System.out.println(i);
}

// 拡張 for: 配列・Iterable の各要素（インデックス不要なとき）
int[] numbers = {10, 20, 30};
for (int number : numbers) {
    System.out.println(number);
}

// while: 回数より「条件が続く限り」
int n = 3;
while (n > 0) {
    n--;
}

// do-while: 本体を最低1回実行してから条件判定
do {
    n++;
} while (n < 2);
```

`for` の `i` のスコープはループまでです（00 章のスコープ）。  
コレクションを回しながら削除する場合は拡張 for ではなく `Iterator`（[03 章](03-collections.md)）を使います。

#### switch 文と switch 式

**伝統的な switch 文**（レガシーコードでよく見る。`break` を忘れると次の case に落ちる）:

```java
int day = 2;
switch (day) {
    case 1:
        System.out.println("月");
        break;
    case 2:
        System.out.println("火");
        break;
    default:
        System.out.println("その他");
        break;
}
```

**switch 式**（Java 14+。値を返し、フォールスルーしない）:

```java
String grade = "B";
String message = switch (grade) {
    case "A" -> "素晴らしい";
    case "B" -> "よくできました";
    default -> "不明な評価";
};
```

新規コードでは式形式が読みやすいです。既存システムの修正では文形式＋`break` を正確に読めることが重要です。

#### break / continue

| 文 | 効果 |
|----|------|
| `break` | 最も内側の `switch` またはループを終了する |
| `continue` | ループの残りの処理を飛ばし、次の周回へ |
| `break ラベル` | 二重ループなどをまとめて抜ける |

```java
for (int i = 1; i <= 10; i++) {
    if (i % 2 != 0) {
        continue; // 奇数は表示しない
    }
    if (i == 8) {
        break;    // 8 で打ち切り
    }
    System.out.println(i);
}
```

ラベル付き（ネストを一気に抜ける）:

```java
outer:
for (int row = 0; row < 3; row++) {
    for (int col = 0; col < 3; col++) {
        if (row == 1 && col == 1) {
            break outer;
        }
    }
}
```

乱用すると流れが追いづらいので、まずは通常の `break` / メソッド分割を優先します。

### 4. メソッド

処理に名前を付け、再利用・テストしやすくする単位です。

```java
static int add(int a, int b) {
    return a + b;
}
```

メソッドは「何をするか」が名前から分かる短さに保ち、1メソッド1責務を意識します。長いメソッドは、後から読む人（未来の自分を含む）のコストになります。

学習中の確認出力には `System.out.println` を使って問題ありません。本番寄りのログ（レベル・出力先の制御）は [05. 入出力とリソース管理](05-io.md) で扱います。

### 5. アクセス修飾子（public / protected / private）

クラス・メソッド・フィールドに「どこから見えるか」を付けます。

| 修飾子 | 同じクラス | 同じパッケージ | サブクラス（別パッケージ） | その他 |
|--------|------------|----------------|----------------------------|--------|
| `private` | ○ | × | × | × |
| （なし）パッケージプライベート | ○ | ○ | × | × |
| `protected` | ○ | ○ | ○ | × |
| `public` | ○ | ○ | ○ | ○ |

```java
public class OrderService {
    // 外部から触らせない状態
    private final OrderRepository repository;

    // アプリ全体から呼ぶ公開 API
    public void placeOrder(Order order) {
        validate(order);
        repository.save(order);
    }

    // 同じクラス（または継承設計）内の補助処理
    private void validate(Order order) {
        if (order == null) {
            throw new IllegalArgumentException("order is required");
        }
    }
}
```

#### それぞれの意味

- **`private`**: そのクラスの内部実装。フィールドや、外に出さないヘルパーメソッドの定番
- **パッケージプライベート（修飾子なし）**: 同じパッケージの仲間だけが使う。モジュール内の協調には便利だが、パッケージ境界が曖昧だと実質 public 化しやすい
- **`protected`**: 継承先に開く。フレームワーク拡張点では有用だが、安易に使うと「継承前提」の結合が強くなる
- **`public`**: 外部契約（API）。一度公開すると、呼び出し側が増えるほど変更コストが跳ね上がる

#### システム開発での効果的な使い方

1. **デフォルトは閉じる（最小公開の原則）**  
   迷ったら `private`。必要になったら一段だけ開ける。最初から全部 `public` にしない。

2. **公開するのは「ユースケース」単位**  
   画面や他モジュールが本当に呼ぶメソッドだけ `public` にする。計算の途中経過や DB 寄りの細部は隠す。

3. **フィールドは原則 `private`**  
   直接代入を許すと、不正な状態（負の在庫、未設定の必須項目など）を防ぎにくい。変更はメソッド経由にし、そこでバリデーションする（02 章のカプセル化へ続く）。

4. **`protected` は継承設計が明確なときだけ**  
   「とりあえず継承用に protected」は避ける。拡張が必要なら、先にインターフェイスや委譲（コンポジション）を検討する。

5. **テストのために何でも public にしない**  
   テストしにくいなら設計を見直す（パッケージプライベート + 同パッケージのテスト、または振る舞いを public API 経由で検証）。実装詳細を公開してテストしやすい状態にするのは本末転倒になりがち。

6. **ライブラリ／モジュール境界では public を契約として扱う**  
   社内共通ライブラリでは、不用意な public が増えると破壊的変更の原因になる。公開 API 一覧を意識し、内部クラスはパッケージプライベートや別モジュールに閉じる。

#### よくある失敗例

| 失敗 | 起きること | 改善 |
|------|------------|------|
| 全メソッド `public` | どこからでも呼べ、依存が絡まる | 入口だけ公開し、他は private |
| フィールド `public` | 不変条件が守れない | private + メソッド |
| テスト用に実装を公開 | API が肥大化し、本番コードがテストに縛られる | 振る舞いテスト or パッケージ設計の見直し |

## サンプル一覧

| ファイル | 内容 |
|----------|------|
| `HelloWorld.java` | 最小プログラム |
| `VariablesAndTypes.java` | 型と演算 |
| `ControlFlow.java` | if / for / while / switch / break / continue |
| `MethodsAndInput.java` | メソッド分割と標準入力 |

## 章末課題

1. 身長(cm)と体重(kg)を入力し、BMI を計算して表示するプログラムを書く
2. 1〜100 の整数のうち、3の倍数だけを出力する
3. 文字列を受け取り、文字数と「空かどうか」を返すメソッドを2つ書く
4. 小さなクラスを1つ作り、フィールドは `private`、外部から使うメソッドだけ `public` にする。なぜその分け方にしたかを短く書く
5. `switch` 文（`break` あり）と `switch` 式の両方で、1〜7 を曜日名に変換する

## チェックリスト

- [ ] `javac` / `java` でサンプルを実行できた
- [ ] `if` / `for` / `switch` / `break` を自分で書ける
- [ ] 伝統的 switch で `break` を忘れると何が起きるか説明できる
- [ ] `main` の役割を説明できる
- [ ] 自分でメソッドを1つ以上追加できた
- [ ] `private` / `public` / `protected` / パッケージプライベートの可視範囲を説明できる
- [ ] 「最小公開」の理由を、変更影響の観点から説明できる

## 次の章

[02. オブジェクト指向](02-oop.md)

前の章: [00. Java の実行の仕組み](00-runtime.md)
