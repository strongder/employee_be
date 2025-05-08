package com.example.employee.dtos.request;

import com.example.employee.model.EmployeeProfile;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CreateBonusRequest {
    private String employeeCode;
    private BigDecimal bonusAmount;
    private String reason;
    private LocalDate bonusDate;
}
