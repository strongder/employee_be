package com.example.employee.controller;

import com.example.employee.dtos.request.BaseSearchRequest;
import com.example.employee.dtos.request.ChangeStatusLeaveRequet;
import com.example.employee.dtos.request.CreateLeaveRequest;
import com.example.employee.dtos.response.ApiResponse;
import com.example.employee.dtos.response.LeaveRequestResponse;
import com.example.employee.service.LeaveRequestService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/leave-requests")
public class LeaveRequestController {

    @Autowired
    private LeaveRequestService leaveRequestService;

    @PostMapping("/create")
    public ApiResponse create(@RequestBody CreateLeaveRequest request) {
        return ApiResponse.success(leaveRequestService.save(request, LeaveRequestResponse.class));
    }
    @PutMapping("/update/{id}")
    public ApiResponse update(@PathVariable Long id, @RequestBody CreateLeaveRequest request) {
        return ApiResponse.success(leaveRequestService.update(id, request, LeaveRequestResponse.class));
    }

    @GetMapping("/{id}")
    public ApiResponse getById(@PathVariable Long id) {
        return ApiResponse.success(leaveRequestService.getById(id, LeaveRequestResponse.class));
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(@PathVariable Long id) {
        return ApiResponse.success(leaveRequestService.softDelete(id));
    }

    @PostMapping("/search")
    public ApiResponse search(@RequestBody BaseSearchRequest request) {
        return ApiResponse.success(leaveRequestService.search(request, LeaveRequestResponse.class));
    }
    @GetMapping("/all")
    public ApiResponse getAll() {
        return ApiResponse.success(leaveRequestService.findAll(LeaveRequestResponse.class));
    }

    @PutMapping("/change-status")
    public ApiResponse changeStatus(@RequestBody ChangeStatusLeaveRequet request) {
        return ApiResponse.success(leaveRequestService.changeStatus(request));
    }

    @GetMapping("/{employeeId}/employee")
    public ApiResponse getLeaveRequestByEmployee(@PathVariable Long employeeId) {
        return ApiResponse.success(leaveRequestService.getLeaveRequestByEmployeeId(employeeId));
    }
}
