package com.fteodoro.webmonitor.dto;

import org.hibernate.validator.constraints.URL;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Positive;

public record CreateEndpointRequest(
    @NotBlank String name,
    @URL String url,
    @Positive int interval
) {}
