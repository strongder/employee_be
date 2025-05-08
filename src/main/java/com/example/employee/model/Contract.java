package com.example.employee.model;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.math.BigDecimal;
import java.time.LocalDate;


@Entity
@Table(name = "contract")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Contract extends BaseEntity{

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @ManyToOne
    @JoinColumn(name = "employee_id", nullable = false)
    private EmployeeProfile employee;
    private String contractCode;
    private String contractType; // "FULL_TIME", "PART_TIME", "INTERNSHIP"
    private String contractStatus; // "ACTIVE", "INACTIVE", "TERMINATED"
    private String contractUrl;
    private LocalDate startDate;
    private LocalDate endDate;
    private BigDecimal monthlySalary;

}
