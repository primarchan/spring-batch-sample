package com.example.hellospringbatch.dto;

import lombok.Data;

@Data
public class PlayerSalaryDto {

    private String ID;

    private String lastName;

    private String firstName;

    private String position;

    private int birthYear;

    private int debutYear;

    private int salary;

    public static PlayerSalaryDto of(PlayerDto playerDto, int salary) {
        PlayerSalaryDto playerSalaryDto = new PlayerSalaryDto();
        playerSalaryDto.setID(playerDto.getID());
        playerSalaryDto.setLastName(playerDto.getLastName());
        playerSalaryDto.setFirstName(playerDto.getFirstName());
        playerSalaryDto.setPosition(playerDto.getPosition());
        playerSalaryDto.setBirthYear(playerDto.getBirthYear());
        playerSalaryDto.setDebutYear(playerDto.getDebutYear());
        playerSalaryDto.setSalary(salary);

        return playerSalaryDto;
    }

}
