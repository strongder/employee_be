package com.example.employee.dtos.response;

import lombok.Data;

@Data
public class AccountResponse {
    private Long id;
    private EmployeeProfileResponse employee;
    private String username;
    private String status;
    private String role;
    private String createdAt;
    private String updatedAt;
    private String createdBy;
    private String updatedBy;
}
