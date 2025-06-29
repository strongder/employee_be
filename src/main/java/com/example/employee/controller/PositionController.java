package com.example.employee.controller;

import com.example.employee.dtos.request.BaseSearchRequest;
import com.example.employee.dtos.request.CreateDepartmentRequest;
import com.example.employee.dtos.request.CreatePositionRequest;
import com.example.employee.dtos.response.ApiResponse;
import com.example.employee.dtos.response.DepartmentResponse;
import com.example.employee.dtos.response.PositionResponse;
import com.example.employee.service.DepartmentService;
import com.example.employee.service.PositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/positions")
public class PositionController {

    @Autowired
    private PositionService positionService;

    // Add other CRUD operations as needed
    @PostMapping("/create")
    public ApiResponse create(@RequestBody CreatePositionRequest request) {
        return ApiResponse.success(positionService.save(request, PositionResponse.class));
    }

    @PutMapping("/update/{id}")
    public ApiResponse update(@PathVariable Long id, @RequestBody CreatePositionRequest request) {
        return ApiResponse.success(positionService.update(id, request, PositionResponse.class));
    }

    @GetMapping("/{id}")
    public ApiResponse getById(@PathVariable Long id) {
        return ApiResponse.success(positionService.getById(id, PositionResponse.class));
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Long id) {
        return ApiResponse.success(positionService.softDelete(id));
    }

    @PostMapping("/search")
    public ApiResponse search(@RequestBody BaseSearchRequest request) {
        return ApiResponse.success(positionService.search(request, PositionResponse.class));
    }

    @GetMapping("/all")
    public ApiResponse getAll() {
        return ApiResponse.success(positionService.findAll());
    }
}
