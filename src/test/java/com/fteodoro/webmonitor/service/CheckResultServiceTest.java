package com.fteodoro.webmonitor.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import java.util.ArrayList;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import com.fteodoro.webmonitor.exception.ResourceNotFoundException;
import com.fteodoro.webmonitor.model.CheckResult;
import com.fteodoro.webmonitor.model.MonitoredEndpoint;
import com.fteodoro.webmonitor.model.ServiceStatus;
import com.fteodoro.webmonitor.repository.CheckResultRepository;

@ExtendWith(MockitoExtension.class)
public class CheckResultServiceTest {

    @Mock
    private CheckResultRepository repository;

    @InjectMocks
    private CheckResultService service;

    @Mock
    private MonitoredEndpointService monitoredEndpointService;

    @Test
    @DisplayName("findByEndpointId Endpoint exists and has results should return a list of CheckResults")
    void findByEndpointId_endpointExistsAndHasResultsShouldReturnListOfCheckResults() {
        var list = new ArrayList<CheckResult>();
        var endpoint = new MonitoredEndpoint("Meu site", "https://fteodoro.dev", 60);
        var result1 = new CheckResult(endpoint, ServiceStatus.UP, 4);
        var result2 = new CheckResult(endpoint, ServiceStatus.UP, 10);
        var result3 = new CheckResult(endpoint, ServiceStatus.DEGRADED, 35);
        list.add(result1);
        list.add(result2);
        list.add(result3);

        when(monitoredEndpointService.findById(anyLong())).thenReturn(endpoint);
        when(repository.findByMonitoredEndpoint_IdOrderByVerifiedAtDesc(anyLong())).thenReturn(list);

        var results = service.findByEndpointId(42L);

        assertEquals(3, results.size());
        verify(repository).findByMonitoredEndpoint_IdOrderByVerifiedAtDesc(42L);
    }

    @Test
    @DisplayName("findByEndpointId Endpoint does not exists should throws ResourceNotFoundException")
    void findByEndpointId_endpointDoesNotExistsShouldThrowsResourceNotFoundException() {
        when(monitoredEndpointService.findById(anyLong()))
            .thenThrow(new ResourceNotFoundException("Not found"));

        assertThrows(ResourceNotFoundException.class, () -> service.findByEndpointId(anyLong()));
        verifyNoMoreInteractions(repository);
    }

    @Test
    @DisplayName("findByEndpointId Endpoint exists should return empty list")
    void findByEndpointId_endpointExistsShouldReturnEmptyList() {
        var endpoint = new MonitoredEndpoint("Meu site", "https://fteodoro.dev", 60);
        when(monitoredEndpointService.findById(anyLong())).thenReturn(endpoint);

        var results = service.findByEndpointId(15L);

        assertTrue(results.isEmpty());
    }
}
