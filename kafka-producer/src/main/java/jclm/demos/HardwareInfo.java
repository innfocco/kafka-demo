package jclm.demos;

import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HWDiskStore;
import oshi.hardware.NetworkIF;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class HardwareInfo {

    private final SystemInfo systemInfo;

    private static final DecimalFormat df = new DecimalFormat("0.00");

    public HardwareInfo() {
        this.systemInfo = new SystemInfo();
    }

    public List<Double> getCpuUsage() {
        CentralProcessor processor = systemInfo.getHardware().getProcessor();
        long[][] longs = new long[8][8];
        double[] cpuUsage = processor.getProcessorCpuLoadBetweenTicks(longs);

        List<Double> cpuUsageList = new ArrayList<>();
        for (double usage : cpuUsage) {
            cpuUsageList.add(usage * 100.0);
        }

        return cpuUsageList;
    }

    public long getMemoryUsage() {
        GlobalMemory memory = systemInfo.getHardware().getMemory();
        return memory.getTotal() - memory.getAvailable();
    }

    public long getDiskActivity() {
        List<HWDiskStore> diskStores = systemInfo.getHardware().getDiskStores();
        long totalDiskActivity = 0;

        for (HWDiskStore diskStore : diskStores) {
            totalDiskActivity += diskStore.getReadBytes() + diskStore.getWriteBytes();
        }

        return totalDiskActivity;
    }

    public long getNetworkUsage(String networkInterfaceName) {
        List<NetworkIF> networkIFs = systemInfo.getHardware().getNetworkIFs();
        long bytesReceived = 0;

        for (NetworkIF networkIF : networkIFs) {
            if (networkIF.getName().equals(networkInterfaceName)) {
                bytesReceived = networkIF.getBytesRecv();
                break; // Break once the desired network interface is found
            }
        }

        return bytesReceived;
    }

}
