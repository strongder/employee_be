package com.example.employee.service;

import com.example.employee.dtos.request.BaseSearchRequest;
import com.example.employee.dtos.request.CreateDepartmentRequest;
import com.example.employee.dtos.response.BaseSearchResponse;
import com.example.employee.dtos.response.DepartmentResponse;
import com.example.employee.model.Department;

public interface DepartmentService extends BaseService<Department, Long> {

    Long softDelete(Long id);

    DepartmentResponse update(Long id, CreateDepartmentRequest request);

    BaseSearchResponse<DepartmentResponse> search(BaseSearchRequest request, Class<DepartmentResponse> responseType);
    Object findAll();
}
