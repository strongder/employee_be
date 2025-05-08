package com.example.employee.dtos.response;

import lombok.Data;

import java.time.LocalDate;

@Data
public class DataFieldEmployee {
    private Long id;
    private String fullName;
    private String avatar;
    private String gender;
    private String code;
    private LocalDate dob;
    private String address;
    private String email;
    private String phone;
}
