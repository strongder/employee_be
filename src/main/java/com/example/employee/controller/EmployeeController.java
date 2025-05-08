package com.example.employee.controller;

import com.example.employee.dtos.request.BaseSearchRequest;
import com.example.employee.dtos.request.ChangeAvatarRequest;
import com.example.employee.dtos.request.CreateEmployeeRequest;
import com.example.employee.dtos.response.ApiResponse;
import com.example.employee.dtos.response.EmployeeProfileResponse;
import com.example.employee.model.EmployeeProfile;
import com.example.employee.service.EmployeeProfileService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/employees")
public class EmployeeController {

    @Autowired
    private EmployeeProfileService employeeService;

    @PostMapping("/create")
    public ApiResponse create(@RequestBody CreateEmployeeRequest request) {
        return ApiResponse.success(employeeService.create(request));
    }

    @PutMapping("/update/{employeeId}")
    public ApiResponse update(@PathVariable("employeeId") Long employeeId, @RequestBody CreateEmployeeRequest request) {
        return ApiResponse.success(employeeService.update(employeeId, request, EmployeeProfileResponse.class));
    }

    @DeleteMapping("/delete/{employeeId}")
    public ApiResponse delete(@PathVariable("employeeId") Long employeeId) {
        return ApiResponse.success(employeeService.softDelete(employeeId));
    }

    @PostMapping("/search")
    public ApiResponse search(@RequestBody BaseSearchRequest request) {
        return ApiResponse.success(employeeService.search(request, EmployeeProfileResponse.class));
    }

    @GetMapping("/current")
    public ApiResponse getCurrentEmployee() {
        return ApiResponse.success(employeeService.getCurrentEmployee());
    }
    @GetMapping("/all")
    public ApiResponse getAll() {
        return ApiResponse.success(employeeService.findAll(EmployeeProfileResponse.class));
    }

    @GetMapping("/{employeeId}")
    public ApiResponse getById(@PathVariable Long employeeId) {
        return ApiResponse.success(employeeService.getById(employeeId, EmployeeProfileResponse.class));
    }

    @PutMapping("/change-avatar")
    public ApiResponse changeAvatar(@ModelAttribute ChangeAvatarRequest avatar) {
        return ApiResponse.success(employeeService.changeAvatar(avatar));
    }

}
