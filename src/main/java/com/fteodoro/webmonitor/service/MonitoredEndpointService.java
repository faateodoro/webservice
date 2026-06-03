package com.fteodoro.webmonitor.service;

import com.fteodoro.webmonitor.exception.ResourceNotFoundException;
import com.fteodoro.webmonitor.model.MonitoredEndpoint;
import com.fteodoro.webmonitor.repository.MonitoredEndpointRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class MonitoredEndpointService {
    private final MonitoredEndpointRepository monitoredEndpointRepository;

    public MonitoredEndpointService(MonitoredEndpointRepository monitoredEndpointRepository) {
        this.monitoredEndpointRepository = monitoredEndpointRepository;
    }

    public void create(String name, String url, int interval) {
        var monitoredEndpoint = new MonitoredEndpoint(name, url, interval);
        monitoredEndpointRepository.save(monitoredEndpoint);
    }

    public List<MonitoredEndpoint> findAll() {
        return monitoredEndpointRepository.findAll();
    }

    public MonitoredEndpoint findById(Long id) {
        return monitoredEndpointRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException("No monitored endpoint found with id %d".formatted(id)));
    }
}
