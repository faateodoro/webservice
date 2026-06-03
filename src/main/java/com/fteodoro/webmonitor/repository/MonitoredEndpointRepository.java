package com.fteodoro.webmonitor.repository;

import com.fteodoro.webmonitor.model.MonitoredEndpoint;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonitoredEndpointRepository extends JpaRepository<MonitoredEndpoint, Long> {
}
