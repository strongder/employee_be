package com.example.employee.dtos.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class ContractResponse {
    private Long id;
    private DataFieldEmployee employee;
    private String contractCode;
    private String contractType; // "FULL_TIME", "PART_TIME", "INTERNSHIP"
    private String contractStatus; // "ACTIVE", "INACTIVE", "TERMINATED"
    private String contractUrl;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal monthlySalary;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
