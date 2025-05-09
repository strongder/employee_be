package com.example.employee.dtos.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CalculatorSalaryResponse {
    private String employeeCode;
    private BigDecimal totalBonus;
    private BigDecimal salary;
    private String monthYear;
}
