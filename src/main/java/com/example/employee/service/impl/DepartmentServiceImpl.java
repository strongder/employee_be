package com.example.employee.service.impl;

import com.example.employee.dtos.request.BaseSearchRequest;
import com.example.employee.dtos.response.BaseSearchResponse;
import com.example.employee.dtos.response.DepartmentResponse;
import com.example.employee.exception.AppException;
import com.example.employee.exception.ErrorResponse;
import com.example.employee.model.Department;
import com.example.employee.repository.DepartmentRepository;
import com.example.employee.service.DepartmentService;
import com.example.employee.utils.SearchUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

@Service
public class DepartmentServiceImpl extends BaseServiceImpl<Department, Long> implements DepartmentService {

    @Autowired
    DepartmentRepository departmentRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private SearchUtils<Department> searchUtils;
    public DepartmentServiceImpl(JpaRepository<Department, Long> repository) {
        super(repository);
        this.departmentRepository = (DepartmentRepository) repository;
    }

    @Override
    protected <Req> Department convertToEntity(Req request) {
        return modelMapper.map(request, Department.class);
    }

    @Override
    protected <Req> Department convertToEntity(Req request, Department existingEntity) {
        return null;
    }

    @Override
    protected <Res> Res convertToResponse(Department entity, Class<Res> responseType) {
        return (Res) modelMapper.map(entity, responseType);
    }

    @Override
    public Long softDelete(Long id) {
        Department department = departmentRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorResponse.ENTITY_NOT_FOUND));
        department.setDeleted(true);
        departmentRepository.save(department);
        return id;
    }

    @Override
    public BaseSearchResponse<DepartmentResponse> search(BaseSearchRequest request, Class<DepartmentResponse> responseType) {
        return searchUtils.search(Department.class,request, department -> convertToResponse(department, responseType));
    }
}
