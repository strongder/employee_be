package com.example.employee.controller;

import com.example.employee.dtos.request.BaseSearchRequest;
import com.example.employee.dtos.request.CreateBonusRequest;
import com.example.employee.dtos.request.CreateSalaryRequest;
import com.example.employee.dtos.response.ApiResponse;
import com.example.employee.dtos.response.SalaryResponse;
import com.example.employee.dtos.response.SalaryResponse;
import com.example.employee.service.SalaryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/salaries")
public class SalaryController {

    @Autowired
    private SalaryService salaryService;

    @PostMapping("/create")
    public ApiResponse create(@RequestBody CreateSalaryRequest request) {
        return ApiResponse.success(salaryService.save(request, SalaryResponse.class));
    }

    @PutMapping("/update/{id}")
    public ApiResponse update(@PathVariable Long id, @RequestBody CreateSalaryRequest request) {
        return ApiResponse.success(salaryService.update(id, request, SalaryResponse.class));
    }

    @GetMapping("/{id}")
    public ApiResponse getById(@PathVariable Long id) {
        return ApiResponse.success(salaryService.getById(id, SalaryResponse.class));
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Long id) {
        return ApiResponse.success(salaryService.softDelete(id));
    }

    @PostMapping("/search")
    public ApiResponse search(@RequestBody BaseSearchRequest request) {
        return ApiResponse.success(salaryService.search(request, SalaryResponse.class));
    }

    @GetMapping("/all")
    public ApiResponse getAll() {
        return ApiResponse.success(salaryService.findAll());
    }

    @GetMapping("/{employeeId}/employee")
    public ApiResponse getCurrentSalary(@PathVariable Long employeeId) {
        return ApiResponse.success(salaryService.getSalaryByEmployeeId(employeeId));
    }

    @GetMapping("/calculate")
    public ApiResponse calculateSalary(@RequestParam String employeeCode, @RequestParam String monthYear) {
        return ApiResponse.success(salaryService.calculateSalary(employeeCode, monthYear));
    }
}
