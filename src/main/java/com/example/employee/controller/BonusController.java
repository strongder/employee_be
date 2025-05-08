package com.example.employee.controller;

import com.example.employee.dtos.request.BaseSearchRequest;
import com.example.employee.dtos.request.CreateBonusRequest;
import com.example.employee.dtos.request.CreateContractRequest;
import com.example.employee.dtos.response.ApiResponse;
import com.example.employee.dtos.response.BonusResponse;
import com.example.employee.dtos.response.ContractResponse;
import com.example.employee.service.BonusService;
import com.example.employee.service.ContractService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/api/v1/bonuses")
public class BonusController {
    @Autowired
    private BonusService bonusService;
    // Add other CRUD operations as needed

    @PostMapping("/create")
    public ApiResponse create(@RequestBody CreateBonusRequest request) {
        return ApiResponse.success(bonusService.save(request, BonusResponse.class));
    }

    @GetMapping("/{id}")
    public ApiResponse getById(@PathVariable Long id) {
        return ApiResponse.success(bonusService.getById(id, BonusResponse.class));
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Long id) {
        return ApiResponse.success(bonusService.softDelete(id));
    }

    @PutMapping("/update/{id}")
    public ApiResponse update(@PathVariable Long id, @RequestBody CreateBonusRequest request) {
        return ApiResponse.success(bonusService.update(id, request, BonusResponse.class));
    }

    @PostMapping("/search")
    public ApiResponse search(@RequestBody BaseSearchRequest request) {
        return ApiResponse.success(bonusService.search(request, BonusResponse.class));
    }

    @GetMapping("/all")
    public ApiResponse getAll() {
        return ApiResponse.success(bonusService.findAll(BonusResponse.class));
    }

    @GetMapping("/{employeeId}/employee")
    public ApiResponse getBonusByEmployee(@PathVariable Long employeeId) {
        return ApiResponse.success(bonusService.getBonusByEmployeeId(employeeId));
    }
}
