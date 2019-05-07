package at.ac.tuwien.infosys.viepepcbackendmonitor;

import com.spotify.docker.client.DockerClient;
import com.spotify.docker.client.exceptions.DockerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;

import java.net.ConnectException;
import java.util.concurrent.TimeUnit;

@Slf4j
public class DockerHelper {

    @Retryable(value = Exception.class, maxAttempts = 20, backoff = @Backoff(delay = 1000, maxDelay = 3000))
    public static boolean checkAvailabilityOfDockerhostWithRetry(DockerClient docker) throws DockerException, InterruptedException {

        if (docker.ping().equals("OK")) {
            return true;
        }

        throw new DockerException("No connection");
    }


}
