FROM docker.io/apache/spark-py:latest
WORKDIR /opt/spark
COPY share/ /share/
CMD ["/opt/spark/bin/pyspark"]
