# Docker 研修環境（Eclipse 連携）

ホスト側に **Eclipse** を用意し、実行基盤（JDK / Maven / PostgreSQL）を Docker で揃える構成です。  
「全員の PC で同じ Java・同じ DB」に寄せて、研修の環境差分を減らすことが目的です。

## 目次

- [構成](#構成)
- [前提](#前提)
- [起動](#起動)
- [接続情報一覧](#接続情報一覧)
- [Eclipse からの使い方](#eclipse-からの使い方)
- [Java サンプルの実行](#java-サンプルの実行)
- [DB 教材の実行](#db-教材の実行)
- [よくあるトラブル](#よくあるトラブル)
- [停止・初期化](#停止初期化)

## 構成

| サービス | 役割 | ホストからの入口 |
|----------|------|------------------|
| `postgres` | DB 教材用 PostgreSQL 16。初回起動で `learning_shop` を初期化 | `localhost:5432` |
| `java-dev` | JDK 17（Temurin）+ Maven + `psql` + SSH | SSH `localhost:2222` |
| `adminer` | SQL 確認用 Web UI | http://localhost:8080 |

```text
[ホスト]
  Eclipse  ←── プロジェクトはリポジトリを直接 Open
       │
       ├─ JDBC ──────────────► postgres:5432
       ├─ SSH / 外部ツール ──► java-dev:2222  (/workspace = リポジトリ)
       └─ ブラウザ ──────────► adminer:8080
```

## 前提

- Docker Desktop（Windows / Mac）または Docker Engine + Compose v2
- ホストに Eclipse（例: Eclipse IDE for Enterprise Java and Web Developers）
- この Git リポジトリをクローン済み

## 起動

```powershell
cd docker
copy .env.example .env   # 初回のみ
docker compose up -d --build
docker compose ps
```

初回はイメージビルドと PostgreSQL 初期化で数分かかることがあります。

## 接続情報一覧

| 項目 | 値（デフォルト） |
|------|------------------|
| DB host (ホスト Eclipse から) | `localhost` |
| DB host (`java-dev` コンテナ内から) | `postgres` |
| DB port | `5432` |
| DB name | `learning_shop` |
| DB user / password | `trainee` / `trainee` |
| JDBC URL (ホスト) | `jdbc:postgresql://localhost:5432/learning_shop` |
| JDBC URL (コンテナ内) | `jdbc:postgresql://postgres:5432/learning_shop` |
| SSH | `ssh trainee@localhost -p 2222` |
| SSH password | `trainee`（`.env` の `TRAINEE_PASSWORD`） |
| Adminer | http://localhost:8080 （System: PostgreSQL, Server: `postgres`） |

パスワードは研修用の弱い値です。公開ネットワークでは使わないでください。

## Eclipse からの使い方

Eclipse は **ホストで動かし**、Docker は **実行環境** として使うのが安定です（Remote 専用 Eclipse よりトラブルが少ないため）。

### パターン A（推奨）: ソースはホスト、DB は Docker

1. Eclipse でリポジトリをインポート  
   - **File → Open Projects from File System…** などで、クローンしたフォルダを開く
2. Java サンプルは、次のどちらかで実行  
   - **A-1**: ホストに JDK 17 を入れ、Eclipse の Installed JREs に登録  
   - **A-2**: 実行だけコンテナに任せる（後述の Terminal / SSH）
3. DB 教材・JDBC は Docker の PostgreSQL へ接続  
   - JDBC: `jdbc:postgresql://localhost:5432/learning_shop`  
   - user/password: `trainee` / `trainee`

JDBC ドライバ（`org.postgresql:postgresql`）は Maven プロジェクト化するか、[Maven Central](https://central.sonatype.com/) から取得してビルドパスへ追加してください（教材 07 / 12 章）。

### パターン B: Eclipse からコンテナへ SSH してコンパイル実行

コンテナ内の JDK / Maven を「研修標準」として使いたい場合。

1. `docker compose up -d` 済みであること
2. ホストの SSH クライアントまたは Eclipse の Terminal / Remote System Explorer で接続

```text
Host: localhost
Port: 2222
User: trainee
Password: trainee
```

3. ログイン後:

```bash
cd /workspace          # リポジトリのルート
java -version
mvn -version
psql -h postgres -U trainee -d learning_shop -c 'SELECT COUNT(*) FROM customers;'
```

Eclipse プラグイン例:

- **TM Terminal** … ホストから `ssh trainee@localhost -p 2222` を叩くだけでも可
- **Remote System Explorer (RSE)** … SSH 接続定義を作成し、リモートでコマンド実行

> ソースは `/workspace` にマウント済みなので、ホストの Eclipse で編集した内容がコンテナですぐ見えます。

### パターン C: Adminer で SQL だけ先に進める

1. http://localhost:8080 を開く
2. System: **PostgreSQL**
3. Server: **postgres**（Docker ネットワーク内のサービス名）
4. User / Password / Database: `trainee` / `trainee` / `learning_shop`

## Java サンプルの実行

コンテナ内の例:

```bash
ssh trainee@localhost -p 2222
cd /workspace/java/samples/01-basics
javac HelloWorld.java
java HelloWorld
```

パッケージ付きサンプル（例: 例外）:

```bash
cd /workspace/java/samples
javac 04-exceptions/com/example/exceptions/*.java
java -cp 04-exceptions com.example.exceptions.ExceptionDemo
```

ホストの Eclipse で「Run As → Java Application」する場合は、ホスト側 JDK 17 を使います（パターン A-1）。

## DB 教材の実行

初期データは Compose 初回起動時に自動投入されます。

```bash
# コンテナ経由
docker compose exec postgres psql -U trainee -d learning_shop -c "SELECT * FROM customers;"

# または java-dev から
docker compose exec java-dev psql -h postgres -U trainee -d learning_shop
# パスワードを聞かれたら trainee
```

演習 SQL:

```bash
docker compose exec -T postgres psql -U trainee -d learning_shop < ../db/samples/exercises/01_select_answers.sql
```

## よくあるトラブル

| 症状 | 対処 |
|------|------|
| `port is already allocated` (5432) | ホストの PostgreSQL を止めるか、`.env` で `POSTGRES_PORT=5433` などに変更 |
| DB が空 / テーブルが無い | 初回以外は init が走らない。`docker compose down -v` でボリューム削除後に再起動（**データ消去**） |
| SSH 接続拒否 | `docker compose ps` で `java-dev` が Up か確認。`docker compose logs java-dev` |
| Windows で entrypoint が `/bin/bash\r` | `git config core.autocrlf false` のうえ、`docker/java-dev/entrypoint.sh` が LF であることを確認 |
| Eclipse から DB 接続失敗 | URL は `localhost`（`postgres` はコンテナ間専用名） |

## 停止・初期化

```powershell
cd docker
docker compose stop          # 停止のみ
docker compose down          # コンテナ削除（DB データは保持）
docker compose down -v       # DB ボリュームも削除して初期状態へ
```

## ファイル構成

```text
docker/
├── compose.yml
├── .env.example
├── README.md
└── java-dev/
    ├── Dockerfile
    └── entrypoint.sh
```
