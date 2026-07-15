package com.fteodoro.webmonitor.controller;

import static org.springframework.http.ResponseEntity.ok;

import com.fteodoro.webmonitor.dto.CheckResultResponse;
import com.fteodoro.webmonitor.dto.CreateEndpointRequest;
import com.fteodoro.webmonitor.dto.EndpointResponse;
import com.fteodoro.webmonitor.model.MonitoredEndpoint;
import com.fteodoro.webmonitor.service.CheckResultService;
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

    private final MonitoredEndpointService monitoredEndpointService;
    private final CheckResultService checkResultService;

    public MonitoredEndpointController(
        MonitoredEndpointService monitoredEndpointService,
        CheckResultService checkResultService
    ) {
        this.monitoredEndpointService = monitoredEndpointService;
        this.checkResultService = checkResultService;
    }

    @PostMapping
    public ResponseEntity<EndpointResponse> create(
        @RequestBody CreateEndpointRequest dto
    ) {
        MonitoredEndpoint monitoredEndpoint = monitoredEndpointService.create(
            dto
        );
        var uri = URI.create("/endpoints/" + monitoredEndpoint.getId());
        return ResponseEntity.created(uri).body(
            EndpointResponse.from(monitoredEndpoint)
        );
    }

    @GetMapping("/{id}")
    public ResponseEntity<EndpointResponse> getById(@PathVariable Long id) {
        var monitoredEndpoint = monitoredEndpointService.findById(id);
        return ok(EndpointResponse.from(monitoredEndpoint));
    }

    @GetMapping
    public ResponseEntity<List<EndpointResponse>> getAll() {
        var monitoredEndpoints = monitoredEndpointService
            .findAll()
            .stream()
            .map(EndpointResponse::from)
            .toList();
        return ok(monitoredEndpoints);
    }

    @GetMapping("/{id}/checks")
    public ResponseEntity<List<CheckResultResponse>> getByMonitoredEndpoint(
        @PathVariable long id
    ) {
        List<CheckResultResponse> responses = checkResultService
            .findByEndpointId(id)
            .stream()
            .map(CheckResultResponse::from)
            .toList();
        return ok(responses);
    }
}
