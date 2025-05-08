package com.example.employee.service;

import com.example.employee.dtos.request.BaseSearchRequest;
import com.example.employee.dtos.request.CreateContractRequest;
import com.example.employee.dtos.response.ContractResponse;
import com.example.employee.model.Contract;

import java.io.IOException;
import java.util.List;

public interface ContractService extends BaseService<Contract, Long> {
    Object search(BaseSearchRequest request, Class<ContractResponse> contractResponseClass);
    ContractResponse create(CreateContractRequest request) throws IOException;
    ContractResponse update(Long id, CreateContractRequest request) throws IOException;

    List<ContractResponse> getContractByEmployeeId(Long employeeId);
}
