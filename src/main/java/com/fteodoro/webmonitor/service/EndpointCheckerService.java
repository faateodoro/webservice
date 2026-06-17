package com.fteodoro.webmonitor.service;

import com.fteodoro.webmonitor.model.CheckResult;
import com.fteodoro.webmonitor.model.MonitoredEndpoint;
import com.fteodoro.webmonitor.model.ServiceStatus;
import com.fteodoro.webmonitor.repository.CheckResultRepository;
import java.time.Instant;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

@Service
public class EndpointCheckerService {

    private final RestClient restClient;
    private final CheckResultRepository checkResultRepository;

    public EndpointCheckerService(
        RestClient restClient,
        CheckResultRepository checkResultRepository
    ) {
        this.restClient = restClient;
        this.checkResultRepository = checkResultRepository;
    }

    public CheckResult check(MonitoredEndpoint endpoint) {
        long begin = Instant.now().toEpochMilli();
        ResponseEntity<Void> response = null;
        ServiceStatus status = null;
        long responseTime;
        String failedWith = null;

        try {
            response = restClient
                .get()
                .uri(endpoint.getUrl())
                .retrieve()
                .onStatus(spec -> true, (req, res) -> {})
                .toBodilessEntity();

            if (response.getStatusCode().is2xxSuccessful()) {
                status = ServiceStatus.UP;
            } else if (response.getStatusCode().is4xxClientError()) {
                status = ServiceStatus.DEGRADED;
            } else if (response.getStatusCode().is5xxServerError()) {
                status = ServiceStatus.DOWN;
            } else {
                status = ServiceStatus.UNKNOWN;
            }
        } catch (ResourceAccessException e) {
            status = ServiceStatus.DOWN;
            failedWith = e.getMessage();
        } finally {
            long end = Instant.now().toEpochMilli();
            responseTime = end - begin;
        }

        return checkResultRepository.save(
            new CheckResult(endpoint, status, (int) responseTime, failedWith)
        );
    }
}
