package com.example.employee.controller;

import com.example.employee.dtos.request.BaseSearchRequest;
import com.example.employee.dtos.request.CreateDepartmentRequest;
import com.example.employee.dtos.response.ApiResponse;
import com.example.employee.dtos.response.DepartmentResponse;
import com.example.employee.service.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/departments")
public class DepartmentController {

     @Autowired
    private DepartmentService departmentService;
    // Add other CRUD operations as needed
     @PostMapping("/create")
    public ApiResponse create(  @RequestBody CreateDepartmentRequest request) {
        return ApiResponse.success(departmentService.save(request, DepartmentResponse.class));
    }

    @PutMapping("/update/{id}")
    public ApiResponse update(@PathVariable Long id, @RequestBody CreateDepartmentRequest request) {
        return ApiResponse.success(departmentService.update(id, request, DepartmentResponse.class));
    }

    @GetMapping("/{id}")
    public ApiResponse getById(@PathVariable Long id) {
        return ApiResponse.success(departmentService.getById(id, DepartmentResponse.class));
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Long id) {
        departmentService.delete(id);
        return ApiResponse.success("Department deleted successfully");
    }

    @PostMapping("/search")
    public ApiResponse search(@RequestBody BaseSearchRequest request) {
        return ApiResponse.success(departmentService.search(request, DepartmentResponse.class));
    }


    @GetMapping("/all")
    public ApiResponse getAll() {
        return ApiResponse.success(departmentService.findAll(DepartmentResponse.class));
    }
}
