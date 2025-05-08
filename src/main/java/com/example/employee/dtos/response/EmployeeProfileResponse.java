package com.example.employee.dtos.response;

import com.example.employee.model.Account;
import com.example.employee.model.Department;
import com.example.employee.model.Position;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
public class EmployeeProfileResponse {
    private Long id;
    private String fullName;
    private String code;
    private String avatar;
    private String gender;
    private LocalDate dob;
    private String address;
    private String email;
    private String phone;
    private String level;
    private String status;
    private LocalDate joinDate;
    private String bankName;
    private String bankAccountNumber;
    private DepartmentResponse department;
    private PositionResponse position;
    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;
    private String createdBy;
    private String updatedBy;
}
