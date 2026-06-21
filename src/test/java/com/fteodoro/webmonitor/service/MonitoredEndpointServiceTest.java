package com.fteodoro.webmonitor.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrowsExactly;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fteodoro.webmonitor.exception.ResourceNotFoundException;
import com.fteodoro.webmonitor.model.MonitoredEndpoint;
import com.fteodoro.webmonitor.repository.MonitoredEndpointRepository;
import java.util.Optional;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public class MonitoredEndpointServiceTest {

    @Mock
    private MonitoredEndpointRepository repository;

    @InjectMocks
    private MonitoredEndpointService service;

    @Test
    @DisplayName("findById Should return correct MonitoredEndpoint")
    void findById_ShouldReturnCorrectMonitoredEndpoint() {
        var endpoint = new MonitoredEndpoint(
            "Awesome site",
            "awesomeurl.com",
            60
        );
        when(repository.findById(anyLong())).thenReturn(Optional.of(endpoint));

        MonitoredEndpoint monitoredEndpoint = service.findById(1L);

        assertEquals(endpoint.getName(), monitoredEndpoint.getName());
        assertEquals(endpoint.getUrl(), monitoredEndpoint.getUrl());
        assertEquals(
            endpoint.getCheckIntervalSeconds(),
            monitoredEndpoint.getCheckIntervalSeconds()
        );

        verify(repository).findById(1L);
    }

    @Test
    @DisplayName(
        "findById Should throws exception when the resource is not found"
    )
    void findById_shouldThrowsExceptionWhenTheResourceIsNotFound() {
        when(repository.findById(anyLong())).thenReturn(Optional.empty());
        String expectedMessage = "No monitored endpoint found with id 15";

        ResourceNotFoundException exception = assertThrowsExactly(
            ResourceNotFoundException.class,
            () -> service.findById(15L)
        );

        assertEquals(expectedMessage, exception.getMessage());
    }
}
