package com.example.employee.dtos.request;

import lombok.Data;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
public class CheckInRequest {
    private Long employeeId;
    private LocalDate date;
}
