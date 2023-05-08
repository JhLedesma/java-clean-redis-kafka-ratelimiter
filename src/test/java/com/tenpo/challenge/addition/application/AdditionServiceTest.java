package com.tenpo.challenge.addition.application;

import com.tenpo.challenge.addition.domain.Addition;
import com.tenpo.challenge.exception.domain.BadRequestException;
import com.tenpo.challenge.percentage.application.PercentageService;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import static org.mockito.Mockito.when;

class AdditionServiceTest {

    private AdditionService additionService;

    @Mock
    private PercentageService percentageService;

    @BeforeEach
    public void setUp() {
        MockitoAnnotations.openMocks(this);
        additionService = new AdditionService(percentageService);
    }

    @Test
    public void testSumSuccess() {
        when(percentageService.getPercentage()).thenReturn(20);
        Addition result = additionService.sum(10, 5);
        Assertions.assertEquals(18.0, result.getTotal());
        Assertions.assertEquals(20, result.getPercentage());
        Assertions.assertEquals(10, result.getValue1());
        Assertions.assertEquals(5, result.getValue2());
    }

    @Test
    public void testSumValuesNegative() {
        when(percentageService.getPercentage()).thenReturn(10);
        Assertions.assertThrows(BadRequestException.class, () -> {
            additionService.sum(-10, 5);
        });
    }

    @Test
    public void testSumValuesZero() {
        when(percentageService.getPercentage()).thenReturn(10);
        Assertions.assertThrows(BadRequestException.class, () -> {
            additionService.sum(0, 0);
        });
    }

    @Test
    public void testSumPercentageZero() {
        when(percentageService.getPercentage()).thenReturn(0);
        Assertions.assertThrows(BadRequestException.class, () -> {
            additionService.sum(10, 5);
        });
    }

    @Test
    public void testSumPercentageNegative() {
        when(percentageService.getPercentage()).thenReturn(-10);
        Assertions.assertThrows(BadRequestException.class, () -> {
            additionService.sum(10, 5);
        });
    }
}