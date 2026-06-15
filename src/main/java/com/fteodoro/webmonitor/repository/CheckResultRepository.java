package com.fteodoro.webmonitor.repository;

import com.fteodoro.webmonitor.model.CheckResult;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CheckResultRepository
    extends JpaRepository<CheckResult, Long>
{
    List<CheckResult> findByMonitoredEndpoint_IdOrderByVerifiedAtDesc(Long id);
}
