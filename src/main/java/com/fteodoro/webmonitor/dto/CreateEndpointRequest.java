package com.fteodoro.webmonitor.dto;

public record CreateEndpointRequest(String name, String url, int interval) {}
