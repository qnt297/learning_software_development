# 04. 例外処理

## 目次

- [この章の目標](#この章の目標)
- [なぜ大事か](#なぜ大事か)
- [学習ポイント](#学習ポイント)
  - [1. Throwable の全体像](#1-throwable-の全体像)
  - [2. Error のケース（アプリ例外とは別物）](#2-error-のケースアプリ例外とは別物)
  - [3. 基本構文](#3-基本構文)
  - [4. プリセット（標準）例外の使い分け](#4-プリセット標準例外の使い分け)
  - [5. カスタム例外](#5-カスタム例外)
  - [6. アンチパターン](#6-アンチパターン)
- [サンプル一覧](#サンプル一覧)
- [章末課題](#章末課題)
- [チェックリスト](#チェックリスト)
- [次の章](#次の章)

## この章の目標

- `Error` / チェック例外 / 非チェック例外の違いを理解する
- 標準ライブラリの代表的な例外を用途で選べる
- カスタム例外を、いつ・どう設計するかを説明できる
- `try / catch / finally` と `throws` を適切に使える

## なぜ大事か

本番障害の多くは「想定外の入力・外部失敗」から始まります。  
例外処理は、失敗を **無視せず、復旧・再試行・ユーザー通知** につなげるための仕組みです。一方、JVM レベルの `Error` まで同じ感覚で catch すると、かえって危険です。

## 学習ポイント

### 1. Throwable の全体像

```text
Throwable
├── Error          … 通常アプリが回復すべきでない深刻な問題
└── Exception
    ├── RuntimeException など（非チェック例外）
    └── それ以外（チェック例外: IOException など）
```

| 種類 | 例 | 扱い |
|------|----|------|
| **Error** | `OutOfMemoryError`, `StackOverflowError`, `NoClassDefFoundError` | 原則 catch しない。原因除去・再起動・設計見直し |
| 非チェック例外 | `IllegalArgumentException`, `NullPointerException`, `IllegalStateException` | 前提違反・プログラマミスが多い。必要箇所で捕捉／防止 |
| チェック例外 | `IOException`, `SQLException` | コンパイル時に対処（catch または throws）を強制 |

### 2. Error のケース（アプリ例外とは別物）

`Error` は「プログラムのロジックで Recover（復旧）するのが難しい／すべきでない」問題を表します。

| Error | 典型原因 | 現場での向き合い方 |
|-------|----------|--------------------|
| `OutOfMemoryError` | ヒープ不足、メモリリーク、巨大データの一括読み込み | ヒープ設定、リーク調査、ストリーム処理、キャッシュ上限。catch して握りつぶさない |
| `StackOverflowError` | 無限再帰、深すぎる呼び出し | 再帰の打ち切り、ループ化、アルゴリズム見直し |
| `NoClassDefFoundError` | 実行時にクラスが見つからない（依存欠落・初期化失敗の余波など） | クラスパス／ビルド／デプロイ成果物の確認 |
| `ExceptionInInitializerError` | static 初期化の失敗 | 初期化処理の例外原因を直す |
| `AssertionError` | `assert` の失敗 | 開発時の前提確認。本番で assert に業務分岐を載せない |

#### なぜ Error を安易に catch してはいけないか

```java
// 悪い例: OOM を握りつぶす
try {
    loadAllIntoMemory();
} catch (OutOfMemoryError e) {
    // ヒープが壊れた状態のまま動き続ける可能性がある
}
```

メモリ不足の後に中途半端に動き続けると、データの不整合や連鎖障害を起こしえます。  
監視・アラート・フェイルファスト（早く失敗して止める）の方が安全なことが多いです。

#### アプリ側でできる予防

- 巨大ファイルは `Files.readAllBytes` 一括ではなく行／チャンク処理（05 章）
- キャッシュに上限と追い出し戦略を持たせる
- 再帰の深さを制限する
- コンテナ／JVM のメモリ設定と実測を合わせる

### 3. 基本構文

```java
try {
    risky();
} catch (IllegalArgumentException e) {
    System.err.println("入力が不正です: " + e.getMessage());
} finally {
    // 成功・失敗に関わらず実行（リソース解放など）
}
```

リソース解放は、可能なら `finally` より **try-with-resources**（05 章）を優先します。

### 4. プリセット（標準）例外の使い分け

JDK が用意している例外を、意味が合うなら優先して使います。独自例外を増やしすぎると、呼び出し側の学習コストが上がります。

#### よく使う非チェック例外

| 例外 | 使う場面 |
|------|----------|
| `IllegalArgumentException` | 引数が契約違反（null、負数、空文字など） |
| `IllegalStateException` | オブジェクトの状態的にその操作が不正（未初期化で実行、二重完了など） |
| `NullPointerException` | 基本は **防ぐ**。自分から投げるなら、より具体的な例外の方が親切なことが多い |
| `IndexOutOfBoundsException` | インデックス範囲外（自作コレクションなど） |
| `UnsupportedOperationException` | 未対応操作（不変リストへの add など） |
| `ArithmeticException` | ゼロ除算など |

#### よく使うチェック例外

| 例外 | 使う場面 |
|------|----------|
| `IOException` | ファイル・ネットワークなど I/O 全般 |
| `FileNotFoundException` | ファイルが無い（IOException の一種） |
| `InterruptedException` | スレッド割り込み（無視せず割り込みフラグを復元する等の作法あり） |

#### 選び方の目安

1. **意味が標準例外と一致する** → 標準を使う  
2. **業務上の失敗として呼び出し側が分岐したい** → カスタム例外  
3. **プログラマのミス（バグ）** → 非チェックで早く落とす  
4. **外部要因で呼び出し側に対処を強制したい** → チェック例外（ただし乱用注意）

近年の多くのアプリでは、ドメイン例外を `RuntimeException` 継承にして、アプリケーション境界（API 層や UI 層）でまとめて扱うスタイルも一般的です。

### 5. カスタム例外

ドメインの失敗を、標準例外より正確に伝えたいときに作ります。

```java
public class InsufficientBalanceException extends RuntimeException {
    private final int balance;
    private final int requested;

    public InsufficientBalanceException(int balance, int requested) {
        super("残高不足: balance=" + balance + ", request=" + requested);
        this.balance = balance;
        this.requested = requested;
    }

    public int getBalance() { return balance; }
    public int getRequested() { return requested; }
}
```

#### カスタム例外を作るとき

- 「残高不足」「在庫なし」「承認待ち」など、**業務用語で捕まえたい** とき
- ログや API レスポンスで、例外型によって処理を分けたいとき
- 追加の文脈（ID、金額、理由コード）をフィールドで持たせたいとき

#### カスタム例外を作らないとき

- 「引数が null」→ `IllegalArgumentException` で十分
- 「まだ実装していない」→ `UnsupportedOperationException`
- 1回しか出ず、型で分岐する予定もないとき（メッセージ付き標準例外で足りる）

#### 設計のコツ

- 名前は `〜Exception` で終わり、**何が起きたか** が分かるようにする
- `Exception` を直接継承するか `RuntimeException` かは、チーム方針と呼び出し側の負担で決める
- 原因例外があるなら `super(message, cause)` で **チェーン** し、根因を残す
- 例外を翻訳するとき（低水準 IO → 業務例外）、元例外を cause に必ず残す

### 6. アンチパターン

- 空の `catch`（握りつぶし）
- すべてを `Exception` や `Throwable` で捕捉して詳細を捨てる
- `Error` を業務例外と同じレイヤで処理する
- 例外を制御フローの通常分岐に使う（例: ループ終端）
- メッセージに機密情報（パスワード、トークン）を載せる
- 調査用に `e.printStackTrace()` だけ散らばせる（本番では Logger に集約する。詳細は [05 章](05-io.md)）

## サンプル一覧

| ファイル | 内容 |
|----------|------|
| `com/example/exceptions/InsufficientBalanceException.java` | カスタム例外 |
| `com/example/exceptions/Account.java` | 例外を投げるドメイン |
| `com/example/exceptions/ExceptionDemo.java` | try-catch のデモ |

## 章末課題

1. 年齢を受け取り、0未満または130超なら例外を投げる `AgeValidator` を作る（標準例外とカスタム、どちらが適切か理由も書く）
2. 複数の `catch` で異なる例外を分けて扱うデモを書く
3. 「なぜ空 catch が危険か」を障害シナリオ付きで説明する
4. `OutOfMemoryError` が起きうるコード例（巨大 List の確保など）を考え、catch せずにどう予防・検知するかを書く

## チェックリスト

- [ ] Error と Exception の違いを説明できる
- [ ] 代表的な標準例外を用途で選べる
- [ ] カスタム例外を「作る／作らない」判断ができる
- [ ] 例外メッセージと cause に調査情報を残せる

## 次の章

[05. 入出力とリソース管理](05-io.md)
