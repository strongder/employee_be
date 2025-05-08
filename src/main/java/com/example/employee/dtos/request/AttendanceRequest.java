package com.example.employee.dtos.request;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class AttendanceRequest {
    private Long employeeId;
    private String status; // Ví dụ: PRESENT, LATE, ABSENT
    private LocalTime checkIn;
    private LocalTime checkOut;
    private LocalDate date;
}
