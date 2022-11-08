FROM docker.io/apache/spark:latest
WORKDIR /opt/spark
COPY share/ /share/
CMD ["/opt/spark/bin/spark-shell"]
