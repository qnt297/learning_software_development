#!/usr/bin/env bash
set -euo pipefail

PASSWORD="${TRAINEE_PASSWORD:-trainee}"
echo "trainee:${PASSWORD}" | chpasswd

# リポジトリをホームからも辿れるようにする（任意）
if [[ -d /workspace ]] && [[ ! -e /home/trainee/workspace ]]; then
  ln -sfn /workspace /home/trainee/workspace
fi

# SSH ホスト鍵（イメージ再ビルドでも足りるが、念のため）
if [[ ! -f /etc/ssh/ssh_host_rsa_key ]]; then
  ssh-keygen -A
fi

# フォアグラウンドではなくバックグラウンドで sshd を起動し、コンテナを維持
/usr/sbin/sshd

echo "=============================================="
echo " java-dev ready"
echo "  SSH : ssh trainee@localhost -p 2222"
echo "  pass: (TRAINEE_PASSWORD / default trainee)"
echo "  work: /workspace  (= リポジトリマウント)"
echo "  JDBC: jdbc:postgresql://postgres:5432/learning_shop"
echo "=============================================="

# Compose から上書きコマンドが来た場合はそれを実行
if [[ $# -gt 0 ]]; then
  exec "$@"
fi

exec sleep infinity
