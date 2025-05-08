package com.example.employee.model;


import com.example.employee.Enum.LeaveStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "leave_request")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class LeaveRequest extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeProfile employee;
    private LocalDate leaveDate;
    private Integer numberOfDays;
    private String reason;
    @Column(columnDefinition = "Text")
    private String description;
    private LeaveStatus status;
    private String approvedBy;
}