package com.fteodoro.webmonitor.dto;

import com.fteodoro.webmonitor.model.MonitoredEndpoint;

public record EndpointResponse(
    Long id,
    String name,
    String url,
    int checkIntervalSeconds
) {
    public static EndpointResponse from(MonitoredEndpoint endpoint) {
        return new EndpointResponse(
            endpoint.getId(),
            endpoint.getName(),
            endpoint.getUrl(),
            endpoint.getCheckIntervalSeconds()
        );
    }
}
