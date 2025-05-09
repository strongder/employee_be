package com.example.employee.service;

import com.example.employee.dtos.request.BaseSearchRequest;
import com.example.employee.dtos.response.BaseSearchResponse;
import com.example.employee.dtos.response.CalculatorSalaryResponse;
import com.example.employee.dtos.response.SalaryResponse;
import com.example.employee.model.Salary;

import java.util.List;

public interface SalaryService extends BaseService<Salary, Long> {

    BaseSearchResponse<SalaryResponse> search(BaseSearchRequest request, Class<SalaryResponse> responseType);

    List<SalaryResponse> getSalaryByEmployeeId(Long employeeId);
    CalculatorSalaryResponse calculateSalary(String employeeCode, String monthYear);
}
