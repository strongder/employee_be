package com.example.employee.dtos.request;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CreateSalaryRequest {
    private String employeeCode;
    private BigDecimal baseSalary; // lương cơ ba
    private BigDecimal allowanceAmount; // phụ cấp
    private BigDecimal bonusAmount; // thưởng
    private BigDecimal deductionAmount; // khấu trừ
    private BigDecimal totalSalary;     // tổng lương
    private LocalDate paymentDate;
    private String status; // "PENDING", "PAID", "CANCELLED"
}
