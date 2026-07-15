package com.fteodoro.webmonitor.dto;

import com.fteodoro.webmonitor.model.CheckResult;
import java.time.LocalDateTime;

public record CheckResultResponse(
    String monitoredEndpointName,
    String monitoredEndpointUrl,
    String serviceStatus,
    LocalDateTime verifiedAt,
    String failedWith
) {
    public static CheckResultResponse from(CheckResult checkResult) {
        return new CheckResultResponse(
            checkResult.getMonitoredEndpoint().getName(),
            checkResult.getMonitoredEndpoint().getUrl(),
            checkResult.getServiceStatus().name(),
            checkResult.getVerifiedAt(),
            checkResult.getFailedWith()
        );
    }
}
