package at.ac.tuwien.infosys.viepepcbackendmonitor;

import com.spotify.docker.client.messages.ContainerStats;

public class MonitorMessageBuilder {

    private long totalMemoryUsage;
    private long totalCpuUsage;

    public MonitorMessageBuilder() {
        this.totalCpuUsage = 0;
        this.totalMemoryUsage = 0;
    }

    public void addValues(ContainerStats containerStats) {
        this.totalCpuUsage = this.totalCpuUsage + containerStats.cpuStats().cpuUsage().totalUsage();
        this.totalMemoryUsage = this.totalMemoryUsage + containerStats.memoryStats().usage();
    }

    public MonitorMessage buildMessage() {
        return new MonitorMessage(this.totalMemoryUsage, this.totalCpuUsage);

    }

}
