package com.example.employee.controller;

import com.example.employee.dtos.request.BaseSearchRequest;
import com.example.employee.dtos.request.CreateContractRequest;
import com.example.employee.dtos.response.ApiResponse;
import com.example.employee.dtos.response.ContractResponse;
import com.example.employee.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/contracts")
public class ContractController {

    @Autowired
    private ContractService contractService;
    // Add other CRUD operations as needed

    @PostMapping(value = "/create", consumes = "multipart/form-data")
    public ApiResponse createContract(@ModelAttribute CreateContractRequest request) throws IOException {
        return ApiResponse.success(contractService.create(request));
    }

    @GetMapping("/{id}")
    public ApiResponse getById(@PathVariable Long id) {
        return ApiResponse.success(contractService.getById(id, ContractResponse.class));
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Long id) {
        return ApiResponse.success(contractService.softDelete(id));
    }
    @PutMapping("/update/{id}")
    public ApiResponse update(@PathVariable Long id, @ModelAttribute CreateContractRequest request) throws IOException {
        return ApiResponse.success(contractService.update(id, request));
    }

    @PostMapping("/search")
    public ApiResponse search(@RequestBody BaseSearchRequest request) {
        return ApiResponse.success(contractService.search(request, ContractResponse.class));
    }

    //getAll
    @GetMapping("/all")
    public ApiResponse getAll() {
        return ApiResponse.success(contractService.findAll(ContractResponse.class));
    }
    @GetMapping("/{employeeId}/employee")
    public ApiResponse getContractByEmployee(@PathVariable Long employeeId) {
        return ApiResponse.success(contractService.getContractByEmployeeId(employeeId));
    }
}
