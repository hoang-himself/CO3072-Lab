alias la='ls -lAh'
alias ls='ls --color=auto --group-directories-first -v'
alias sudo='sudo '
KAFKA_BROKER_SERVER='broker-1:29091'
alias kt='kafka-topics.sh --bootstrap-server ${KAFKA_BROKER_SERVER}'
alias kcp='kafka-console-producer.sh --bootstrap-server ${KAFKA_BROKER_SERVER}'
alias kcc='kafka-console-consumer.sh --bootstrap-server ${KAFKA_BROKER_SERVER}'