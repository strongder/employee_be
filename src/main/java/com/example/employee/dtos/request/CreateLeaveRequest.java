package com.example.employee.dtos.request;

import com.example.employee.Enum.LeaveStatus;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateLeaveRequest {
    private Long id;
    private String employeeId;
    private LocalDate leaveDate;
    private Integer numberOfDays;
    private String reason;
    private LeaveStatus status;
    private String description;
}
