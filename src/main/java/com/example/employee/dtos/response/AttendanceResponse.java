package com.example.employee.dtos.response;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;

@Data
public class AttendanceResponse {
    private Long id;
    private EmployeeProfileResponse employee;
    private LocalDate date;
    private LocalTime checkIn;
    private LocalTime checkOut;
    private String status; // Ví dụ: PRESENT, LATE, ABSENT
    private float workHours;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
    // Getters and Setters
}
