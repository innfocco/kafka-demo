package jclm.demos;

import com.google.gson.Gson;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.serialization.StringSerializer;

import java.util.Properties;

public class HardwareInfoProducer {

    private Properties kafkaConfig;

    private KafkaProducer<String, String> producer;

    public HardwareInfoProducer(){
        this.kafkaConfig = new Properties();
        this.kafkaConfig.setProperty(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "127.0.0.1:9092");
        this.kafkaConfig.setProperty(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        this.kafkaConfig.setProperty(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
    }

    public void connect(){
        this.producer = new KafkaProducer<>(this.kafkaConfig);
    }

    public void send(String topic, InfoDTO message){
        String value = asJSON(message);
        ProducerRecord<String, String> producerRecord = new ProducerRecord<>(topic, value);
        this.producer.send(producerRecord);
    }

    private String asJSON(InfoDTO message) {
        Gson gson = new Gson();
        return gson.toJson(message);
    }

    public void disconnect() {
        this.producer.close();
        this.producer.flush();
    }
}
