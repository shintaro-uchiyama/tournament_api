FROM mysql:8.0.11

# 設定ファイルを配置
# (MySQLは設定ファイルの権限が777だと読み込まない)
COPY config /etc/mysql/conf.d/
RUN chmod 644 /etc/mysql/conf.d/*

RUN apt-get update && \
    apt-get install -y locales && \
    rm -rf /var/lib/apt/lists/* && \
    echo "ja_JP.UTF-8 UTF-8" > /etc/locale.gen && \
    locale-gen ja_JP.UTF-8
ENV LC_ALL ja_JP.UTF-8
