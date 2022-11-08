alias la='ls -lAh'
alias ls='ls --color=auto --group-directories-first -v'
alias sudo='sudo '
KAFKA_BROKER_SERVER='broker-1:29091'
alias kt='kafka-topics.sh --bootstrap-server ${KAFKA_BROKER_SERVER}'
alias kcp='kafka-console-producer.sh --bootstrap-server ${KAFKA_BROKER_SERVER}'
alias kcc='kafka-console-consumer.sh --bootstrap-server ${KAFKA_BROKER_SERVER}'

export HADOOP_HOME=/home/hadoop/hadoop
export PATH=${PATH}:${HADOOP_HOME}/bin:${HADOOP_HOME}/sbin
export JAVA_HOME=/usr/lib/jvm/java-openjdk
export HADOOP_CLASSPATH=$JAVA_HOME/lib/tools.jar
export SPARK_HOME=/home/hadoop/spark
export PATH=${PATH}:$SPARK_HOME/bin:$SPARK_HOME/sbin:/usr/lib/scala/bin
