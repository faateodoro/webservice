package com.fteodoro.webmonitor.scheduler;

import com.fteodoro.webmonitor.model.MonitoredEndpoint;
import com.fteodoro.webmonitor.service.EndpointCheckerService;
import com.fteodoro.webmonitor.service.MonitoredEndpointService;
import java.util.List;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
public class MonitoringScheduler {

    private final EndpointCheckerService endpointCheckerService;
    private final MonitoredEndpointService monitoredEndpointService;

    public MonitoringScheduler(
        MonitoredEndpointService monitoredEndpointService,
        EndpointCheckerService endpointCheckerService
    ) {
        this.endpointCheckerService = endpointCheckerService;
        this.monitoredEndpointService = monitoredEndpointService;
    }

    @Scheduled(fixedDelay = 30000)
    public void scheduleChecking() {
        List<MonitoredEndpoint> endpoints =
            monitoredEndpointService.findAllActive();
        endpoints.forEach(endpointCheckerService::check);
    }
}
