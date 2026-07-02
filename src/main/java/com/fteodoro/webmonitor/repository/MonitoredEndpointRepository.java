package com.fteodoro.webmonitor.repository;

import com.fteodoro.webmonitor.model.MonitoredEndpoint;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MonitoredEndpointRepository
    extends JpaRepository<MonitoredEndpoint, Long>
{
    List<MonitoredEndpoint> findByIsActiveTrue();
}
