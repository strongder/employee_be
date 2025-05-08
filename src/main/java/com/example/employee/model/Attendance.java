package com.example.employee.model;


import com.example.employee.Enum.AttendanceStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "attendance")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attendance extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeProfile employee;
    private LocalDate date;
    private LocalTime checkIn;
    private LocalTime checkOut;
    @Enumerated(EnumType.STRING)
    private AttendanceStatus status;
    private float workHours;
}

