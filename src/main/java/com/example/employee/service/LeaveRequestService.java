package com.example.employee.service;

import com.example.employee.dtos.request.BaseSearchRequest;
import com.example.employee.dtos.request.ChangeStatusLeaveRequet;
import com.example.employee.dtos.request.CreateLeaveRequest;
import com.example.employee.dtos.response.BaseSearchResponse;
import com.example.employee.dtos.response.LeaveRequestResponse;
import com.example.employee.model.Department;
import com.example.employee.model.LeaveRequest;

import java.util.List;

public interface LeaveRequestService extends BaseService<LeaveRequest, Long> {
    LeaveRequestResponse save(CreateLeaveRequest request, Class<LeaveRequestResponse> responseType);

    // Additional methods specific to Department can be added here
    BaseSearchResponse<LeaveRequestResponse> search(BaseSearchRequest request, Class<LeaveRequestResponse> responseType);

    Object changeStatus(ChangeStatusLeaveRequet request);

    List<LeaveRequestResponse> getLeaveRequestByEmployeeId(Long employeeId);

    Object findAll();
}
