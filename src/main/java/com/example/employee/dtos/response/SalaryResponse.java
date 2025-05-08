package com.example.employee.dtos.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class SalaryResponse {
    private long id;
    private DataFieldEmployee employee;
    private BigDecimal baseSalary; // lương cơ ba
    private BigDecimal allowanceAmount; // phụ cấp
    private BigDecimal bonusAmount; // thưởng
    private BigDecimal deductionAmount; // khấu trừ
    private BigDecimal totalSalary;     // tổng lương
    private LocalDate paymentDate;
    private String status; // "PENDING", "PAID", "CANCELLED"
}
