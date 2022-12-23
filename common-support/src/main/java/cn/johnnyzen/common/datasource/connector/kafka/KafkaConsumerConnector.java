package cn.johnnyzen.common.datasource.connector.kafka;

import org.apache.kafka.clients.consumer.*;
import org.apache.kafka.common.PartitionInfo;
import org.apache.kafka.common.TopicPartition;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * @author 408675 (tai.zeng@seres.cn)
 * @version v1.0
 * @project bdp-data-service-parent
 * @create-date 2022/8/10 11:56
 * @description 1个 Connector 对象 即 一个产生一个 connection 连接实例
 *
 */

public class KafkaConsumerConnector<K, V> {
    private final static Logger LOG = LoggerFactory.getLogger(KafkaConsumerConnector.class);

    public KafkaConsumer<K, V> getConsumer(Properties properties) {
        return new KafkaConsumer<K, V>(properties);
    }

    /**
     * 获取 Kafka topic 的 分区数
     *
     * @param topic
     * @param consumer
     * @return
     */
    public static <K, V> int getPartitionNum(String topic, KafkaConsumer<K, V> consumer) {
        return getPartitionsInfo(topic, consumer).size();
    }

    /**
     * 获取 kafka topic 分区信息
     */
    public static <K, V> Collection<PartitionInfo> getPartitionsInfo(String topic, KafkaConsumer<K, V> consumer) {
        return consumer.partitionsFor(topic);
    }

    public static <K, V> void setOffset(long fetchDataTime, String topic, KafkaConsumer<K, V> consumer) {
        //long fetchDataTime = LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant().toEpochMilli();

        // 获取 topic 的 partition 信息
        Collection<PartitionInfo> partitionInfos = consumer.partitionsFor(topic);
        //Integer partitionNum = partitionInfos.size(); // topic 的 分区数
        List<TopicPartition> topicPartitions = new ArrayList<>();
        Map<TopicPartition, Long> timestampsToSearch = new HashMap<>();// 即 partitionMap
        for (PartitionInfo currentPartitionInfo : partitionInfos) {
            topicPartitions.add(new TopicPartition(currentPartitionInfo.topic(), currentPartitionInfo.partition()));
            timestampsToSearch.put(new TopicPartition(currentPartitionInfo.topic(), currentPartitionInfo.partition()), fetchDataTime);
        }
        consumer.assign(topicPartitions);// assign可绑定 partition 和 topic | Kafka手动分区分配assign(Collection)不能和自动分区分配subscribe(Collection, ConsumerRebalanceListener)一起使用

        // 获取每个 partition 偏移量
        Map<TopicPartition, OffsetAndTimestamp> timeOffsetMap = consumer.offsetsForTimes(timestampsToSearch);

        Set<Map.Entry<TopicPartition, OffsetAndTimestamp>> entries = timeOffsetMap.entrySet();
        /**
         * 在调用seek方法的时候,需要先获得分区的信息,而分区的信息要通过poll方法来获得.
         * 如果调用seek方法时,没有分区信息,则会抛出 IllegalStateException 异常 No current assignment for partition xxxx.
         */
/*        for (Map.Entry<TopicPartition, OffsetAndTimestamp> entry : entries) {
            if(entry.getValue() == null) {
                consumer.seek(new TopicPartition(entry.getKey().topic(),entry.getKey().partition()), 99);
                continue;
            }
            consumer.seek(new TopicPartition(entry.getKey().topic(),entry.getKey().partition()), entry.getValue().offset());
        }*/
        OffsetAndTimestamp offsetTimestamp = null;
        for (Map.Entry<TopicPartition, OffsetAndTimestamp> entry : entries) {
            // 如果设置的查询偏移量的时间点 > 最大的索引记录时间，则: value 为空
            offsetTimestamp = entry.getValue();
            if (offsetTimestamp != null) {
                int partition = entry.getKey().partition();
                long timestamp = offsetTimestamp.timestamp();
                long offset = offsetTimestamp.offset();
                // 设置读取消息的偏移量
                consumer.seek(entry.getKey(), offset);
            }
        }

        //设置各分区初始偏移量结束
    }
}
