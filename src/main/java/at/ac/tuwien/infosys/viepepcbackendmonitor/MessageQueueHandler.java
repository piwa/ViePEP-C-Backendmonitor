package at.ac.tuwien.infosys.viepepcbackendmonitor;

import com.spotify.docker.client.messages.ContainerStats;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Component;

@Slf4j
@Component
public class MessageQueueHandler {


    @Value("${monitor.queue.name}")
    private String queueName;
    @Value("${viepepc.vm.intern.id}")
    private String vmInternId;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Retryable(value = Exception.class, maxAttempts = 20, backoff=@Backoff(delay=1000, maxDelay=3000))
    public void sendMessage(MonitorMessage monitorMessage) {

        rabbitTemplate.convertAndSend(queueName, monitorMessage);
    }

}
