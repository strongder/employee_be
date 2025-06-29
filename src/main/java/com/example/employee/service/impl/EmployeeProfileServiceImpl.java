package com.example.employee.service.impl;

import com.example.employee.dtos.request.BaseSearchRequest;
import com.example.employee.dtos.request.ChangeAvatarRequest;
import com.example.employee.dtos.request.CreateEmployeeRequest;
import com.example.employee.dtos.response.BaseSearchResponse;
import com.example.employee.dtos.response.EmployeeProfileResponse;
import com.example.employee.exception.AppException;
import com.example.employee.exception.ErrorResponse;
import com.example.employee.model.Account;
import com.example.employee.model.Contract;
import com.example.employee.model.EmployeeProfile;
import com.example.employee.repository.AccountRepository;
import com.example.employee.repository.DepartmentRepository;
import com.example.employee.repository.EmployeeProfileRepository;
import com.example.employee.repository.PositionRepository;
import com.example.employee.service.EmployeeProfileService;
import com.example.employee.service.FileService;
import com.example.employee.utils.SearchUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class EmployeeProfileServiceImpl extends BaseServiceImpl<EmployeeProfile, Long> implements EmployeeProfileService {

    @Autowired
    EmployeeProfileRepository employeeProfileRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private AccountRepository accountRepository;
    @Autowired
    private FileService fileService;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private PositionRepository positionRepository;
    @Autowired
    private SearchUtils<EmployeeProfile> searchUtils;

    public EmployeeProfileServiceImpl(JpaRepository<EmployeeProfile, Long> repository) {
        super(repository);
        this.employeeProfileRepository = (EmployeeProfileRepository) repository;
    }


    @Override
    public EmployeeProfileResponse create(CreateEmployeeRequest request) {
        EmployeeProfile employee = convertToEntity(request);
        employee.setCode(generationEmployeeCode());
        employeeProfileRepository.save(employee);
        return convertToResponse(employee, EmployeeProfileResponse.class);
    }
    @Override
    protected <Req> EmployeeProfile convertToEntity(Req request) {
        CreateEmployeeRequest employeeRequest = (CreateEmployeeRequest) request;
        EmployeeProfile employee = modelMapper.map(request, EmployeeProfile.class);
        employee.setDepartment(departmentRepository.findById(employeeRequest.getDepartmentId())
                .orElseThrow(() -> new AppException(ErrorResponse.ENTITY_NOT_FOUND)));
        employee.setPosition(positionRepository.findById(employeeRequest.getPositionId())
                .orElseThrow(() -> new AppException(ErrorResponse.ENTITY_NOT_FOUND)));
        return employee;
    }

    @Override
    protected <Req> EmployeeProfile convertToEntity(Req request, EmployeeProfile existingEntity) {
        CreateEmployeeRequest employeeRequest = (CreateEmployeeRequest) request;
        modelMapper.map(request, existingEntity);
        existingEntity.setDepartment(departmentRepository.findById(employeeRequest.getDepartmentId())
                .orElseThrow(() -> new AppException(ErrorResponse.ENTITY_NOT_FOUND)));
        existingEntity.setPosition(positionRepository.findById(employeeRequest.getPositionId())
                .orElseThrow(() -> new AppException(ErrorResponse.ENTITY_NOT_FOUND)));
        return existingEntity;
    }

    @Override
    protected <Res> Res convertToResponse(EmployeeProfile entity, Class<Res> responseType) {
        return (Res) modelMapper.map(entity, responseType);
    }

    @Override
    public EmployeeProfileResponse getCurrentEmployee() {
//        String username = SecurityContextHolder.getContext().getAuthentication().getName();
//        Account account = accountRepository.findByUsername(username)
//                .orElseThrow(() -> new AppException(ErrorResponse.ENTITY_NOT_FOUND));
//        EmployeeProfile employee = accountRepository.find(account);x
//        return convertToResponse(employee, EmployeeProfileResponse.class);
        return null;
    }

    @Override
    public Long softDelete(Long id) {
        EmployeeProfile employee = employeeProfileRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorResponse.ENTITY_NOT_FOUND));
        employee.setDeleted(true);
        employeeProfileRepository.save(employee);
        return id;
    }

    @Override
    public BaseSearchResponse<EmployeeProfileResponse> search(BaseSearchRequest request, Class<EmployeeProfileResponse> responseType) {
        return searchUtils.search(EmployeeProfile.class, request, employee -> convertToResponse(employee, responseType));
    }

    @Override
    public Object findAll() {
        List<EmployeeProfile> lists = employeeProfileRepository.findEmployeeProfilesByDeleteFalse();
        return lists.stream()
                .map(employee -> convertToResponse(employee, EmployeeProfileResponse.class))
                .toList();
    }

    @Override
    public String changeAvatar(ChangeAvatarRequest request) {
        EmployeeProfile employeeProfile = employeeProfileRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new AppException(ErrorResponse.ENTITY_NOT_FOUND));
        uploadFileAsync(request.getAvatar(), employeeProfile);
        return employeeProfile.getAvatar();
    }

    @Async
    public void uploadFileAsync(MultipartFile file, EmployeeProfile employeeProfile) {
        try {
            String fileName = fileService.uploadFile(file); // upload file bình thường
            employeeProfile.setAvatar(fileName);
            employeeProfileRepository.save(employeeProfile);
        } catch (IOException e) {
            // log error
        }
    }

    public String generationEmployeeCode()
    {
        LocalDate now = LocalDate.now();
        String datePart = now.format(DateTimeFormatter.ofPattern("yyyy"));
        // Đếm số nhân viên đã tạo trong tháng hiện tại
        long countThisMonth = employeeProfileRepository.countByMonth(now.getYear());
        long nextNumber = countThisMonth + 1;
        return String.format("EMP%s%04d", datePart, nextNumber); // Ví dụ: EMP20240003
    }

}
