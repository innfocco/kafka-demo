package jclm.demos;

public class Main {
    private static boolean producing = true;

    public static void main(String[] args) throws InterruptedException {
        HardwareInfoProducer producer = new HardwareInfoProducer();
        producer.connect();
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            System.out.println("Starting graceful shutdown...");
            producing = false;
        }));
        System.out.println("Sending telemetry data to Kafka...");
        try {
            while (producing){
                HardwareInfo hardwareInfo = new HardwareInfo();
                InfoDTO message = new InfoDTO(hardwareInfo.getCpuUsage(), hardwareInfo.getMemoryUsage(),
                        hardwareInfo.getDiskActivity(), hardwareInfo.getNetworkUsage("wlp0s20f3"));
                producer.send("telemetry_topic", message);
                Thread.sleep(250);
            }
        } finally {
            producer.disconnect();
        }
    }
}