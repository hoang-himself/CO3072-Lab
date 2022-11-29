package com.hpcc.streaminglab;

import java.util.Arrays;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.common.serialization.StringDeserializer;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaInputDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka010.ConsumerStrategies;
import org.apache.spark.streaming.kafka010.KafkaUtils;
import org.apache.spark.streaming.kafka010.LocationStrategies;
import scala.Serializable;
import scala.Tuple2;

public class WordCount implements Serializable {
  private static final String BOOTSTRAP_SERVERS = "localhost:9092";
  private static final String TOPIC_NAME = "topic_1952255";
  private static final Collection<String> TOPICS = Arrays.asList(TOPIC_NAME.split(","));

  public static void main(String[] args) throws Exception {

    Map<String, Object> kafkaParams = new HashMap<>();
    kafkaParams.put("bootstrap.servers", BOOTSTRAP_SERVERS);
    kafkaParams.put("key.deserializer", StringDeserializer.class);
    kafkaParams.put("value.deserializer", StringDeserializer.class);
    kafkaParams.put("group.id", "use_a_separate_group_id_for_each_stream");
    kafkaParams.put("auto.offset.reset", "latest");
    kafkaParams.put("enable.auto.commit", false);

    SparkConf conf = new SparkConf().setMaster("local[2]")
        .setAppName("WordCount");
    JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(3));
    jssc.sparkContext().setLogLevel("WARN");
    jssc.checkpoint("/tmp/1952255");

    // <topic, message>
    JavaInputDStream<ConsumerRecord<String, String>> stream = KafkaUtils.createDirectStream(
        jssc,
        LocationStrategies.PreferConsistent(),
        ConsumerStrategies.Subscribe(TOPICS, kafkaParams));

    JavaDStream<String> lines = stream.map(ConsumerRecord::value);
    JavaDStream<String> words = lines
        .flatMap((FlatMapFunction<String, String>) x -> Arrays.asList(x.split(" ")).iterator());
    JavaPairDStream<String, Integer> pairs = words
        .mapToPair((PairFunction<String, String, Integer>) s -> new Tuple2<>(s, 1));
    JavaPairDStream<String, Integer> wordCounts = pairs
        .reduceByKey((Function2<Integer, Integer, Integer>) (i1, i2) -> i1 + i2);

    wordCounts.print();

    jssc.start();
    jssc.awaitTermination();
  }
}
