package cn.johnnyzen.common.datasource.connector.kafka;

/**
 * @author 408675 (tai.zeng@seres.cn)
 * @version v1.0
 * @project bdp-data-service-parent
 * @create-time 2022/10/6 13:07
 * @description Kafka 常量
 */
public class KafkaConstant {
    public final static String BROKERLIST = "metadata.broker.list"; // kafka的host:port,host:port
    public final static String SERIALIZERCLASS = "serializer.class";// value 的 序列化类
    public final static String REQUESTACKS = "request.required.acks";//
    public final static String REQUESTTIMEOUTMS = "request.timeout.ms";
    public final static String CODEC = "compression.codec";
    public final static String PRODUCERTYPE = "producer.type";
    public final static String TOPIC = "kafka.topic";

    // 仅在async情况下使用
    public final static String QUEUEMAXMS = "queue.buffering.max.ms";
    public final static String QUEUEMAXMESSAGE = "queue.buffering.max.message";
    public final static String QUEUETIMEOUTMS = "queue.enqueue.timeout.ms";
    public final static String QUEUEBATCHNUM = "batch.num.messages";

    // 消费者专用
    public final static String GROUP_ID = "group.id";// 消费者组id
    public final static String ZKCONNECT = "zookeeper.connect";// zk地址
    public final static String AUTOCOMMIT_OFFSET = "enable.auto.commit";// 是否自动提交offset
    public final static String COMMIT_OFFSET_INTERVAL_MS = "auto.commit.interval.ms";// 自动提交offset的时间间隔
    public final static String AOTU_OFFSET_RESET = "auto.offset.reset";// 当zk无消费者所在组的offset时，怎么读取消息（smallest--从最早的开始读，largest--读消费者启动之后的）

    public final static String ZKCONNECT_TOPIC = "zookeeper.connect.";// zk地址
    public final static String GROUP_ID_TOPIC = "group.id.";
    public final static String CONSUMER_THREAD_TOPIC = "consumer.thread.";
    public final static String AUTO_OFFSET_RESET_TOPIC = "auto.offset.reset.";
    public final static String AUTOCOMMIT_OFFSET_TOPIC = "enable.auto.commit.";
    public final static String COMMIT_OFFSET_INTERVAL_MS_TOPIC = "auto.commit.interval.ms.";

    public final static String BLOCKINGQUEUE_SIZE_TOPIC = "blocking_queue_size.";
}
