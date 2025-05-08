package com.example.employee.dtos.request;

import com.example.employee.dtos.response.AccountResponse;
import com.example.employee.model.Account;
import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

import java.math.BigDecimal;
import java.time.LocalDate;

@Data
public class CreateContractRequest {
    private Long id;
    private String employeeCode;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate startDate;
    @JsonFormat(shape = JsonFormat.Shape.STRING, pattern = "dd/MM/yyyy")
    private LocalDate endDate;
    private String contractType; // "FULL_TIME", "PART_TIME", "INTERNSHIP"
    private String contractStatus; // "ACTIVE", "INACTIVE", "TERMINATED"
    private MultipartFile contractFile;
    private BigDecimal monthlySalary;
}
