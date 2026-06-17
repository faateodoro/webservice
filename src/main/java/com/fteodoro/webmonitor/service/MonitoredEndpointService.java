package com.fteodoro.webmonitor.service;

import com.fteodoro.webmonitor.dto.CreateEndpointRequest;
import com.fteodoro.webmonitor.exception.ResourceNotFoundException;
import com.fteodoro.webmonitor.model.MonitoredEndpoint;
import com.fteodoro.webmonitor.repository.MonitoredEndpointRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class MonitoredEndpointService {

    private final MonitoredEndpointRepository monitoredEndpointRepository;

    public MonitoredEndpointService(
        MonitoredEndpointRepository monitoredEndpointRepository
    ) {
        this.monitoredEndpointRepository = monitoredEndpointRepository;
    }

    public MonitoredEndpoint create(CreateEndpointRequest dto) {
        var monitoredEndpoint = new MonitoredEndpoint(
            dto.name(),
            dto.url(),
            dto.interval()
        );
        return monitoredEndpointRepository.save(monitoredEndpoint);
    }

    public List<MonitoredEndpoint> findAll() {
        return monitoredEndpointRepository.findAll();
    }

    public MonitoredEndpoint findById(Long id) {
        return monitoredEndpointRepository
            .findById(id)
            .orElseThrow(() ->
                new ResourceNotFoundException(
                    "No monitored endpoint found with id %d".formatted(id)
                )
            );
    }
}
