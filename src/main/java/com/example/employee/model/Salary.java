package com.example.employee.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Salary extends BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeProfile employee;
    private BigDecimal baseSalary; // lương cơ ba
    private BigDecimal allowanceAmount; // phụ cấp
    private BigDecimal bonusAmount; // thưởng
    private BigDecimal deductionAmount; // khấu trừ
    private BigDecimal totalSalary;     // tổng lương
    private LocalDate paymentDate;
    private String status; // "PENDING", "PAID", "CANCELLED"
}
