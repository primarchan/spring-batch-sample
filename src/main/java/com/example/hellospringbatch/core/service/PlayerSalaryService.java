package com.example.hellospringbatch.core.service;

import com.example.hellospringbatch.dto.PlayerDto;
import com.example.hellospringbatch.dto.PlayerSalaryDto;
import org.springframework.stereotype.Service;

import java.time.Year;

@Service
public class PlayerSalaryService {

    public PlayerSalaryDto calculateSalary(PlayerDto playerDto) {
        int salary = (Year.now().getValue() - playerDto.getBirthYear()) * 1_000_000;

        return PlayerSalaryDto.of(playerDto, salary);
    }

}
