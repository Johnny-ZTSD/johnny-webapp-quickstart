package cn.johnnyzen.bigdata.datasets.medical.csv2mysql.handler;

/*
import org.apache.kafka.common.serialization.Serdes;
import org.apache.kafka.streams.KafkaStreams;
import org.apache.kafka.streams.StreamsBuilder;
import org.apache.kafka.streams.StreamsConfig;
import org.apache.kafka.streams.Topology;
import org.apache.kafka.streams.kstream.KStream;
import org.apache.kafka.streams.kstream.KTable;
import org.apache.kafka.streams.kstream.Produced;

import java.util.List;
import java.util.Properties;
*/

/**
 * @project: JohnnyWebappQuickstart
 * @author: Johnny
 * @date: 2022/6/20  20:14:58
 * @description: ...
 */

/*
public class KafkaStreamSample {
    private static final String INPUT_TOPIC = "input-topic";
    private static final String OUTPUT_TOPIC = "output-topic";

    //构建配置属性
    public static Properties getProperties() {
        Properties properties = new Properties();
        properties.put(StreamsConfig.BOOTSTRAP_SERVERS_CONFIG, "49.232.153.84:9092");
        properties.put(StreamsConfig.APPLICATION_ID_CONFIG, "wordcount-app");
        properties.put(StreamsConfig.DEFAULT_KEY_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        properties.put(StreamsConfig.DEFAULT_VALUE_SERDE_CLASS_CONFIG, Serdes.String().getClass());
        return properties;
    }

    public static KafkaStreams createKafkaStreams() {
        Properties properties = getProperties();
        // 构建流结构拓扑
        StreamsBuilder builder = new StreamsBuilder();
        // 构建wordCount这个Processor
        wordCountStream(builder);
        Topology topology = builder.build();
        // 构建KafkaStreams
        return new KafkaStreams(topology, properties);
    }
*/
    /**
     * 定义流计算过程
     * 例子为词频统计
     *//*
    public static void wordCountStream(StreamsBuilder builder) {
        // 不断的从INPUT_TOPIC上获取新的数据，并追加到流上的一个抽象对象
        KStream<String, String> source = builder.stream(INPUT_TOPIC);
        // KTable是数据集的抽象对象
        KTable<String, Long> count = source.flatMapValues(
                // 以空格为分隔符将字符串进行拆分
                line -> List.of(line.toLowerCase().split(" "))
                // 按value进行分组统计
        ).groupBy((k, v) -> v).count();

        KStream<String, Long> sink = count.toStream();
        // 将统计结果输出到OUTPUT_TOPIC
        sink.to(OUTPUT_TOPIC, Produced.with(Serdes.String(), Serdes.Long()));
    }

    public static void main(String[] args) {
        KafkaStreams streams = createKafkaStreams();
        // 启动该Stream
        streams.start();
    }
}
*/

