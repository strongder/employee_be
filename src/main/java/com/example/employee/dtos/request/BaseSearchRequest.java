package com.example.employee.dtos.request;

import lombok.Data;

import java.time.LocalDate;

@Data
public class BaseSearchRequest {
    private String keyword;
    private String value;
    private String keyword2;
    private String value2;
    private int page = 1;
    private int size = 10;
    private String sortBy = "createdAt";
    private String sortDir = "desc";
    private LocalDate startDate;
    private LocalDate endDate;

}
