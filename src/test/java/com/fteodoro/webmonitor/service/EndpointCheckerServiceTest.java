package com.fteodoro.webmonitor.service;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import com.fteodoro.webmonitor.model.CheckResult;
import com.fteodoro.webmonitor.model.MonitoredEndpoint;
import com.fteodoro.webmonitor.model.ServiceStatus;
import com.fteodoro.webmonitor.repository.CheckResultRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Answers;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.ResourceAccessException;
import org.springframework.web.client.RestClient;

@ExtendWith(MockitoExtension.class)
public class EndpointCheckerServiceTest {

    @Mock(answer = Answers.RETURNS_DEEP_STUBS)
    private RestClient restClient;

    @InjectMocks
    private EndpointCheckerService service;

    @Mock
    private CheckResultRepository repository;

    private MonitoredEndpoint endpoint;

    @BeforeEach
    void setUp() {
        endpoint = new MonitoredEndpoint(
            "Um endpoint interessante",
            "interestingurl.com",
            90
        );
        when(repository.save(any(CheckResult.class))).thenAnswer(invocation ->
            invocation.getArgument(0, CheckResult.class)
        );
    }

    @Test
    @DisplayName("check Should return ServiceStatus.UP when HttpStatus is OK")
    void check_shouldReturnServiceStatusUPWhenHttpStatusIsOK() {
        when(
            restClient
                .get()
                .uri(anyString())
                .retrieve()
                .onStatus(any(), any())
                .toBodilessEntity()
        ).thenReturn(new ResponseEntity<Void>(HttpStatus.OK));

        CheckResult result = service.check(endpoint);

        assertEquals(ServiceStatus.UP, result.getServiceStatus());
        verify(repository).save(any(CheckResult.class));
    }

    @Test
    @DisplayName(
        "check Should return ServiceStatus.DEGRADED when HttpStatus is 4XX"
    )
    void check_shouldReturnServiceStatusDEGRADEDWhenHttpStatusIs4XX() {
        when(
            restClient
                .get()
                .uri(anyString())
                .retrieve()
                .onStatus(any(), any())
                .toBodilessEntity()
        ).thenReturn(new ResponseEntity<Void>(HttpStatus.BAD_REQUEST));

        CheckResult result = service.check(endpoint);

        assertEquals(ServiceStatus.DEGRADED, result.getServiceStatus());
        verify(repository).save(any(CheckResult.class));
    }

    @Test
    @DisplayName(
        "check Should return ServiceStatus.DOWN when HttpStatus is 5XX"
    )
    void check_shouldReturnServiceStatusDOWNWhenHttpStatusIs5XX() {
        when(
            restClient
                .get()
                .uri(anyString())
                .retrieve()
                .onStatus(any(), any())
                .toBodilessEntity()
        ).thenReturn(
            new ResponseEntity<Void>(HttpStatus.INTERNAL_SERVER_ERROR)
        );

        CheckResult result = service.check(endpoint);

        assertEquals(ServiceStatus.DOWN, result.getServiceStatus());
        verify(repository).save(any(CheckResult.class));
    }

    @Test
    @DisplayName(
        "check Should return ServiceStatus.DOWN when return ResourceAccessException"
    )
    void check_shouldReturnServiceStatusDOWNWhenReturnResourceAccessException() {
        when(
            restClient
                .get()
                .uri(anyString())
                .retrieve()
                .onStatus(any(), any())
                .toBodilessEntity()
        ).thenThrow(new ResourceAccessException("Timeout"));

        CheckResult result = service.check(endpoint);

        assertEquals(ServiceStatus.DOWN, result.getServiceStatus());
        verify(repository).save(any(CheckResult.class));
    }
}
