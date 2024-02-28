package jclm.demos;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;

public class Main {
    private static boolean consuming = true;

    public static void main(String[] args) throws InterruptedException {
        final HardwareInfoConsumer consumer = new HardwareInfoConsumer();
        consumer.connect("telemetry_topic");
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Starting graceful shutdown...");
            consuming = false;
        }));
        try{
            while(consuming){
                synchronized (consumer) {
                    ConsumerRecords<String, String> records = consumer.read();
                    for (ConsumerRecord<String, String> record : records) {
                        System.out.println(record);
                    }
                    System.out.print("**** Waiting for telemetry messages **** ");
                    System.out.print("\r");
                    Thread.sleep(250);
                }
            }
        } finally {
            consumer.disconnect();
        }
    }
}