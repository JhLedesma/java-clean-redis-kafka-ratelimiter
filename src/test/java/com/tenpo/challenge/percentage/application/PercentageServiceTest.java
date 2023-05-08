package com.tenpo.challenge.percentage.application;

import com.tenpo.challenge.exception.domain.BadGatewayException;
import com.tenpo.challenge.percentage.domain.PercentageCache;
import com.tenpo.challenge.percentage.domain.PercentageClient;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

public class PercentageServiceTest {

    @Mock
    private PercentageClient percentageClient;

    @Mock
    private PercentageCache percentageCache;

    @InjectMocks
    private PercentageService percentageService;

    @BeforeEach
    public void setup() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    @DisplayName("Should get percentage from cache")
    public void shouldGetPercentageFromCache() {
        int expectedPercentage = 10;
        when(percentageCache.getPercentage()).thenReturn(Optional.of(expectedPercentage));
        int result = percentageService.getPercentage();
        assertEquals(expectedPercentage, result);
        verify(percentageCache, times(1)).getPercentage();
        verifyNoInteractions(percentageClient);
    }

    @Test
    @DisplayName("Should get percentage from client")
    public void shouldGetPercentageFromClient() {
        int expectedPercentage = 10;
        when(percentageCache.getPercentage()).thenReturn(Optional.empty());
        when(percentageClient.getPercentage()).thenReturn(expectedPercentage);
        when(percentageCache.save(any(Integer.class))).thenReturn(expectedPercentage);
        int result = percentageService.getPercentage();
        assertEquals(expectedPercentage, result);
        verify(percentageCache, times(1)).getPercentage();
        verify(percentageClient, times(1)).getPercentage();
        verify(percentageCache, times(1)).save(expectedPercentage);
    }

    @Test
    @DisplayName("Should get percentage from client and return last used when fails")
    public void shouldGetPercentageFromClientAndReturnLastUsedWhenFails() {
        when(percentageCache.getPercentage()).thenReturn(Optional.empty());
        when(percentageClient.getPercentage()).thenThrow(new RuntimeException("Client error"));
        when(percentageCache.getLastUsedPercentage()).thenReturn(Optional.of(10));
        int result = percentageService.getPercentage();
        assertEquals(10, result);
        verify(percentageCache, times(1)).getPercentage();
        verify(percentageClient, times(1)).getPercentage();
        verify(percentageCache, times(1)).getLastUsedPercentage();
    }

    @Test
    @DisplayName("Should throw BadGatewayException when client fails and there is no last used percentage")
    public void shouldThrowBadGatewayExceptionWhenClientFailsAndNoLastUsedPercentage() {
        when(percentageCache.getPercentage()).thenReturn(Optional.empty());
        when(percentageClient.getPercentage()).thenThrow(new RuntimeException("Client error"));
        when(percentageCache.getLastUsedPercentage()).thenReturn(Optional.empty());
        assertThrows(BadGatewayException.class, () -> percentageService.getPercentage());
        verify(percentageCache, times(1)).getPercentage();
        verify(percentageClient, times(1)).getPercentage();
        verify(percentageCache, times(1)).getLastUsedPercentage();
    }
}
