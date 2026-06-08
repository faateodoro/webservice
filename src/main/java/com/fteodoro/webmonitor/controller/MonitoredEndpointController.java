package com.fteodoro.webmonitor.controller;

import com.fteodoro.webmonitor.dto.CreateEndpointRequest;
import com.fteodoro.webmonitor.dto.EndpointResponse;
import com.fteodoro.webmonitor.model.MonitoredEndpoint;
import com.fteodoro.webmonitor.service.MonitoredEndpointService;
import java.net.URI;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/endpoints")
public class MonitoredEndpointController {

    private final MonitoredEndpointService service;

    public MonitoredEndpointController(MonitoredEndpointService service) {
        this.service = service;
    }

    @PostMapping
    public ResponseEntity<EndpointResponse> create(
        @RequestBody CreateEndpointRequest dto
    ) {
        MonitoredEndpoint monitoredEndpoint = service.create(dto);
        var uri = URI.create("/endpoints/" + monitoredEndpoint.getId());
        return ResponseEntity.created(uri).body(
            EndpointResponse.from(monitoredEndpoint)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EndpointResponse> getById(@PathVariable Long id) {
        var monitoredEndpoint = service.findById(id);
        return ResponseEntity.ok(EndpointResponse.from(monitoredEndpoint));
    }

    @GetMapping
    public ResponseEntity<List<EndpointResponse>> getAll() {
        var monitoredEndpoints = service
            .findAll()
            .stream()
            .map(EndpointResponse::from)
            .toList();
        return ResponseEntity.ok(monitoredEndpoints);
    }
}
