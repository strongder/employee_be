package com.example.employee.dtos.request;

import lombok.Data;

@Data
public class CreateDepartmentRequest {
    private String name;
    private String description;
}
