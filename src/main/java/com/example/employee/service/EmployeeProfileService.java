package com.example.employee.service;

import com.example.employee.dtos.request.BaseSearchRequest;
import com.example.employee.dtos.request.ChangeAvatarRequest;
import com.example.employee.dtos.request.CreateEmployeeRequest;
import com.example.employee.dtos.response.BaseSearchResponse;
import com.example.employee.dtos.response.EmployeeProfileResponse;
import com.example.employee.model.EmployeeProfile;

public interface EmployeeProfileService extends BaseService<EmployeeProfile, Long> {
    // Additional methods specific to Department can be added here
    EmployeeProfileResponse getCurrentEmployee();

    Long softDelete(Long id);
    EmployeeProfileResponse create(CreateEmployeeRequest request);

    BaseSearchResponse<EmployeeProfileResponse> search(BaseSearchRequest request, Class<EmployeeProfileResponse> responseType);

    String changeAvatar(ChangeAvatarRequest avatar);
}
