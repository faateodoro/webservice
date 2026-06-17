package com.fteodoro.webmonitor.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import java.time.LocalDateTime;
import org.hibernate.annotations.CreationTimestamp;

@Entity
public class CheckResult {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "endpoint_id", nullable = false)
    private MonitoredEndpoint monitoredEndpoint;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private ServiceStatus serviceStatus;

    @Column(nullable = false)
    private int responseTime;

    @CreationTimestamp
    private LocalDateTime verifiedAt;

    private String failedWith;

    protected CheckResult() {}

    public CheckResult(
        MonitoredEndpoint monitoredEndpoint,
        ServiceStatus serviceStatus,
        int responseTime
    ) {
        this.monitoredEndpoint = monitoredEndpoint;
        this.serviceStatus = serviceStatus;
        this.responseTime = responseTime;
    }

    public CheckResult(
        MonitoredEndpoint monitoredEndpoint,
        ServiceStatus serviceStatus,
        int responseTime,
        String failedWith
    ) {
        this.monitoredEndpoint = monitoredEndpoint;
        this.serviceStatus = serviceStatus;
        this.responseTime = responseTime;
        this.failedWith = failedWith;
    }

    public Long getId() {
        return id;
    }

    public MonitoredEndpoint getMonitoredEndpoint() {
        return monitoredEndpoint;
    }

    public ServiceStatus getServiceStatus() {
        return serviceStatus;
    }

    public int getResponseTime() {
        return responseTime;
    }

    public LocalDateTime getVerifiedAt() {
        return verifiedAt;
    }

    public String getFailedWith() {
        return failedWith;
    }
}
