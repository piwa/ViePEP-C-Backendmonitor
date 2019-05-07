package at.ac.tuwien.infosys.viepepcbackendmonitor;

import com.spotify.docker.client.DefaultDockerClient;
import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerException;
import com.spotify.docker.client.messages.Container;
import com.spotify.docker.client.messages.ContainerStats;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.concurrent.TimeUnit;

@Slf4j
@Component
public class Monitor {

    @Autowired
    private MessageQueueHandler messageQueueHandler;

    @Scheduled(fixedRate = 60000)
    public void monitor() {

        try {
//            final DockerClient docker = DefaultDockerClient.builder().uri("http://35.207.116.195:2375").connectTimeoutMillis(60000).build();
            final DockerClient docker = new DefaultDockerClient("unix:///var/run/docker.sock");


            if(DockerHelper.checkAvailabilityOfDockerhostWithRetry(docker)) {

                List<Container> containerList = docker.listContainers(DockerClient.ListContainersParam.withStatusRunning());

                MonitorMessageBuilder monitorMessageBuilder = new MonitorMessageBuilder();

                for (Container container : containerList) {
                    ContainerStats stats = docker.stats(container.id());
                    monitorMessageBuilder.addValues(stats);
                    log.debug("Container: " + container.id() + "; cpuTotalUsage: " + stats.toString());
                }

                log.info("Send monitoring data: " + monitorMessageBuilder.buildMessage().toString());
                messageQueueHandler.sendMessage(monitorMessageBuilder.buildMessage());
            }

        } catch (InterruptedException | DockerException e) {
            log.error("Exception", e);
        }

    }


}
