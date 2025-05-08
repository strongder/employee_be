package com.example.employee.dtos.request;

import lombok.Data;

@Data
public class CreateAccountRequest {
    private String username;
    private String employeeCode;
    private String password;
    private String status;
    private String role;
}
