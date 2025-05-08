package com.example.employee.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "employee_profile")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class EmployeeProfile extends  BaseEntity{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String fullName;
    private String code;
    private String gender;
    private String avatar;
    private LocalDate dob;
    private String address;
    private String email;
    private String phone;
    private String status = "ACTIVE";
    private LocalDate joinDate;
    private String level;
    private String bankName;
    private String bankAccountNumber;
    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    @ManyToOne
    @JoinColumn(name = "position_id")
    private Position position;




}
