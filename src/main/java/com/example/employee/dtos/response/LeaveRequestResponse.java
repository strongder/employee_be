package com.example.employee.dtos.response;

import com.example.employee.Enum.LeaveStatus;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class LeaveRequestResponse {
    private Long id;
    private DataFieldEmployee employee;
    private LocalDate leaveDate;
    private Integer numberOfDays;
    private String reason;
    private LeaveStatus status;
    private String description;
    private String approvedBy;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
