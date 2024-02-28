package jclm.demos;

import java.util.List;

public record InfoDTO(List<Double> cpuUsage, long memoryUsage, long diskActivity, long networkUsage) {
}
