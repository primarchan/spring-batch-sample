package com.example.hellospringbatch.core.service;

import com.example.hellospringbatch.dto.PlayerDto;
import com.example.hellospringbatch.dto.PlayerSalaryDto;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockedStatic;

import java.time.Year;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

class PlayerSalaryServiceTest {

    private PlayerSalaryService playerSalaryService;

    @BeforeEach
    public void setUp() {
        playerSalaryService = new PlayerSalaryService();
    }

    @Test
    public void calculateSalary() {
        // given
        MockedStatic<Year> mockYearClass = mockStatic(Year.class);
        Year mockYear = mock(Year.class);
        when(mockYear.getValue()).thenReturn(2023);
        mockYearClass.when(Year::now).thenReturn(mockYear);

        PlayerDto mockPlayer = mock(PlayerDto.class);
        when(mockPlayer.getBirthYear()).thenReturn(1980);

        // when
        PlayerSalaryDto result = playerSalaryService.calculateSalary(mockPlayer);

        // then
        Assertions.assertEquals(result.getSalary(), 430_000_00);
    }

}