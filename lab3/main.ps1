Set-Location StreamingLab
mvn package
spark-submit --packages org.apache.spark:spark-sql-kafka-0-10_2.12:3.0.2 --class com.hpcc.streaminglab.WordCount .\target\StreamingLab-1.0-SNAPSHOT-jar-with-dependencies.jar
