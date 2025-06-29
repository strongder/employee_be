package com.example.employee.service.impl;

import com.example.employee.dtos.request.BaseSearchRequest;
import com.example.employee.dtos.request.ChangeStatusLeaveRequet;
import com.example.employee.dtos.request.CreateLeaveRequest;
import com.example.employee.dtos.response.BaseSearchResponse;
import com.example.employee.dtos.response.LeaveRequestResponse;
import com.example.employee.exception.AppException;
import com.example.employee.exception.ErrorResponse;
import com.example.employee.model.LeaveRequest;
import com.example.employee.Enum.LeaveStatus;
import com.example.employee.repository.EmployeeProfileRepository;
import com.example.employee.repository.LeaveRequestRepository;
import com.example.employee.service.LeaveRequestService;
import com.example.employee.utils.SearchUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LeaveRequestServiceImpl extends BaseServiceImpl<LeaveRequest, Long> implements LeaveRequestService {

    @Autowired
    LeaveRequestRepository leaveRequestRepository;

    @Autowired
    private EmployeeProfileRepository employeeProfileRepository;
    @Autowired
    private ModelMapper modelMapper;
    private SearchUtils<LeaveRequest> searchUtils;

    public LeaveRequestServiceImpl(JpaRepository<LeaveRequest, Long> repository) {
        super(repository);
        this.leaveRequestRepository = (LeaveRequestRepository) repository;
    }

    @Override
    public LeaveRequestResponse save(CreateLeaveRequest request, Class<LeaveRequestResponse> responseType) {
        LeaveRequest leaveRequest = convertToEntity(request);
        leaveRequest.setStatus(LeaveStatus.PENDING);
        leaveRequestRepository.save(leaveRequest);
        return convertToResponse(leaveRequest, responseType);
    }

    @Override
    protected <Req> LeaveRequest convertToEntity(Req request) {
        CreateLeaveRequest leaveRequest = (CreateLeaveRequest) request;
        LeaveRequest leave = modelMapper.map(leaveRequest, LeaveRequest.class);
        leave.setEmployee(employeeProfileRepository.findById(Long.valueOf(leaveRequest.getEmployeeId()))
                .orElseThrow(() -> new AppException(ErrorResponse.ENTITY_NOT_FOUND)));
        return leave;
    }

    @Override
    protected <Req> LeaveRequest convertToEntity(Req request, LeaveRequest existingEntity) {
        CreateLeaveRequest leaveRequest = (CreateLeaveRequest) request;
        modelMapper.map(leaveRequest, existingEntity);
        existingEntity.setEmployee(employeeProfileRepository.findById(Long.valueOf(leaveRequest.getEmployeeId()))
                .orElseThrow(() -> new AppException(ErrorResponse.ENTITY_NOT_FOUND)));
        return existingEntity;
    }

    @Override
    protected <Res> Res convertToResponse(LeaveRequest entity, Class<Res> responseType) {
        return modelMapper.map(entity, responseType);
    }

    @Override
    public Long softDelete(Long id) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorResponse.ENTITY_NOT_FOUND));
        leaveRequest.setDeleted(true);
        leaveRequestRepository.save(leaveRequest);
        return id;
    }

    @Override
    public BaseSearchResponse<LeaveRequestResponse> search(BaseSearchRequest request, Class<LeaveRequestResponse> responseType) {
        return searchUtils.search(LeaveRequest.class, request, leaveRequest -> convertToResponse(leaveRequest, responseType));
    }

    @Override
    public LeaveRequestResponse changeStatus(ChangeStatusLeaveRequet requet) {
        LeaveRequest leaveRequest = leaveRequestRepository.findById(requet.getLeaveRequestId())
                .orElseThrow(() -> new AppException(ErrorResponse.ENTITY_NOT_FOUND));
        leaveRequest.setStatus(requet.getStatus());
        leaveRequestRepository.save(leaveRequest);
        return convertToResponse(leaveRequest, LeaveRequestResponse.class);
    }
    @Override
    public List<LeaveRequestResponse> getLeaveRequestByEmployeeId(Long employeeId) {
        List<LeaveRequest> leaveRequests = leaveRequestRepository.findByEmployeeId(employeeId);
        return leaveRequests.stream().map(
                leave -> convertToResponse(leave, LeaveRequestResponse.class)
        ).toList();
    }

    @Override
    public Object findAll() {
        List<LeaveRequest> lists = leaveRequestRepository.findLeaveRequestsByDeletedFalse();
        return lists.stream()
                .map(leave -> convertToResponse(leave, LeaveRequestResponse.class))
                .toList();
    }
}
