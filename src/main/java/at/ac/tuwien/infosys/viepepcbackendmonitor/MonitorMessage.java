package at.ac.tuwien.infosys.viepepcbackendmonitor;

import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.ToString;

import java.io.Serializable;

@Data
public class MonitorMessage  implements Serializable {

    private final Long totalMemoryUsage;
    private final Long totalCpuUsage;

}
