package com.example.employee.dtos.request;

import lombok.Data;

@Data
public class CreatePositionRequest {
    private String name;
    private String description;
}
