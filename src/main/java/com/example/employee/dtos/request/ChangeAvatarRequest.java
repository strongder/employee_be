package com.example.employee.dtos.request;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;

@Data
public class ChangeAvatarRequest {
    private Long employeeId;
    private MultipartFile avatar;
}
