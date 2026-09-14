# Java 研修用コンテナ
# Eclipse からは SSH (ホストの 2222) または ボリューム共有で利用する想定
FROM eclipse-temurin:17-jdk-jammy

ENV DEBIAN_FRONTEND=noninteractive \
    LANG=C.UTF-8 \
    JAVA_HOME=/opt/java/openjdk \
    MAVEN_HOME=/usr/share/maven \
    PATH="/opt/java/openjdk/bin:${PATH}"

RUN apt-get update \
    && apt-get install -y --no-install-recommends \
        openssh-server \
        sudo \
        git \
        curl \
        ca-certificates \
        maven \
        postgresql-client \
        vim \
        unzip \
    && rm -rf /var/lib/apt/lists/* \
    && mkdir -p /var/run/sshd \
    && ssh-keygen -A \
    && useradd -m -s /bin/bash -u 1000 trainee \
    && echo "trainee ALL=(ALL) NOPASSWD:ALL" > /etc/sudoers.d/trainee \
    && chmod 0440 /etc/sudoers.d/trainee \
    && mkdir -p /workspace \
    && chown trainee:trainee /workspace

# パスワード認証を研修用に許可（本番では鍵認証へ）
RUN sed -i 's/#PasswordAuthentication yes/PasswordAuthentication yes/' /etc/ssh/sshd_config \
    && sed -i 's/PasswordAuthentication no/PasswordAuthentication yes/' /etc/ssh/sshd_config \
    && sed -i 's/#PermitRootLogin prohibit-password/PermitRootLogin no/' /etc/ssh/sshd_config \
    && echo "AllowUsers trainee" >> /etc/ssh/sshd_config

COPY entrypoint.sh /usr/local/bin/entrypoint.sh
RUN chmod +x /usr/local/bin/entrypoint.sh

WORKDIR /workspace
EXPOSE 22

ENTRYPOINT ["/usr/local/bin/entrypoint.sh"]
