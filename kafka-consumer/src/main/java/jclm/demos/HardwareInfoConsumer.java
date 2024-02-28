package jclm.demos;

import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.common.serialization.StringDeserializer;

import java.time.Duration;
import java.util.Arrays;
import java.util.Properties;

public class HardwareInfoConsumer {

    private Properties kafkaConfig;

    private KafkaConsumer<String, String> consumer;

    public HardwareInfoConsumer(){
        this.kafkaConfig = new Properties();
        this.kafkaConfig.setProperty(ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        this.kafkaConfig.setProperty(ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        this.kafkaConfig.setProperty(ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, StringDeserializer.class.getName());
        this.kafkaConfig.setProperty(ConsumerConfig.GROUP_ID_CONFIG, "TELEMETRY_CONSUMERS");
        this.kafkaConfig.setProperty(ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, "latest");
    }

    public void connect(String topic){
        this.consumer = new KafkaConsumer<>(this.kafkaConfig);
        this.consumer.subscribe(Arrays.asList(topic));
    }

    public ConsumerRecords<String, String> read(){
        return this.consumer.poll(Duration.ofMillis(1000));
    }

    public synchronized void disconnect() {
        this.consumer.commitSync();
        this.consumer.close();
    }
}
