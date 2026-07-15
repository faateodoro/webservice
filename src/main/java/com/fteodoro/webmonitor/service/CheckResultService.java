package com.fteodoro.webmonitor.service;

import com.fteodoro.webmonitor.model.CheckResult;
import com.fteodoro.webmonitor.repository.CheckResultRepository;
import java.util.List;
import org.springframework.stereotype.Service;

@Service
public class CheckResultService {

    private final CheckResultRepository checkResultRepository;
    private final MonitoredEndpointService monitoredEndpointService;

    public CheckResultService(
        CheckResultRepository checkResultRepository,
        MonitoredEndpointService monitoredEndpointService
    ) {
        this.checkResultRepository = checkResultRepository;
        this.monitoredEndpointService = monitoredEndpointService;
    }

    public List<CheckResult> findByEndpointId(long id) {
        this.monitoredEndpointService.findById(id);
        return checkResultRepository.findByMonitoredEndpoint_IdOrderByVerifiedAtDesc(
            id
        );
    }
}
