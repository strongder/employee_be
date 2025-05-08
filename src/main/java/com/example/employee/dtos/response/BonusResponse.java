package com.example.employee.dtos.response;

import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class BonusResponse {
    private Long id;
    private DataFieldEmployee employee;
    private BigDecimal bonusAmount;
    private String reason;
    private LocalDate bonusDate;
    private boolean isDeleted;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
