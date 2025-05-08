package com.example.employee.dtos.request;

import com.example.employee.model.Account;
import com.example.employee.model.Department;
import com.example.employee.model.Position;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CreateEmployeeRequest {
    private Long id;
    private String fullName;
    private String gender;
    private LocalDate dob;
    private String address;
    private String email;
    private LocalDate joinDate;
    private String phone;
    private String level;
    private String bankName;
    private String bankAccountNumber;
    private Long departmentId;
    private Long positionId;
}
