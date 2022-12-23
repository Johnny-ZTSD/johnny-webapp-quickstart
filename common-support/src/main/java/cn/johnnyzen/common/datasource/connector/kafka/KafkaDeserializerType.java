package cn.johnnyzen.common.datasource.connector.kafka;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author 408675 (tai.zeng@seres.cn)
 * @version v1.0
 * @project bdp-data-service-parent
 * @create-time 2022/9/22 23:06
 * @description 反序列化器
 *  1) 相关的 Kafka配置项:
 *      org.apache.kafka.clients.consumer.ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG ("key.deserializer")
 *      org.apache.kafka.clients.consumer.ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG ("value.deserializer")
 */
public enum KafkaDeserializerType {
    BYTE_ARRAY_DESERIALIZER("BYTE_ARRAY_DESERIALIZER","org.apache.kafka.common.serialization.ByteArrayDeserializer"),
    STRING_DESERIALIZER("STRING_DESERIALIZER","org.apache.kafka.common.serialization.StringDeserializer");

    private final String code;
    private final String name;

    KafkaDeserializerType(String code, String name){
        this.code = code;
        this.name = name;
    }

    public static KafkaDeserializerType findByCode(String code) {
        for (KafkaDeserializerType type : values()) {
            if (type.getCode().equals(code)) {
                return type;
            }
        }
        return null;
    }

    public static KafkaDeserializerType findByName(String name) {
        for (KafkaDeserializerType type : values()) {
            if (type.getName().equals(name)) {
                return type;
            }
        }
        return null;
    }

    public String getCode() {
        return this.code;
    }

    public String getName() {
        return this.name;
    }

    public static List<Map<String, String>> toList() {
        List<Map<String, String>> list = new ArrayList();
        for (KafkaDeserializerType item : KafkaDeserializerType.values()) {
            Map<String, String> map = new HashMap<String, String>();
            map.put("code", item.getCode());
            map.put("name", item.getName());
            list.add(map);
        }
        return list;
    }
}