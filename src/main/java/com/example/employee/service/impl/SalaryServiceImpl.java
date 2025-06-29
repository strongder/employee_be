package com.example.employee.service.impl;

import com.example.employee.dtos.request.BaseSearchRequest;
import com.example.employee.dtos.request.CreateSalaryRequest;
import com.example.employee.dtos.response.BaseSearchResponse;
import com.example.employee.dtos.response.CalculatorSalaryResponse;
import com.example.employee.dtos.response.SalaryResponse;
import com.example.employee.exception.AppException;
import com.example.employee.exception.ErrorResponse;
import com.example.employee.model.Contract;
import com.example.employee.model.EmployeeProfile;
import com.example.employee.model.Salary;
import com.example.employee.repository.ContractRepository;
import com.example.employee.repository.EmployeeProfileRepository;
import com.example.employee.repository.SalaryRepository;
import com.example.employee.service.SalaryService;
import com.example.employee.utils.SearchUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;

@Service
public class SalaryServiceImpl extends BaseServiceImpl<Salary, Long> implements SalaryService {

    @Autowired
    SalaryRepository salaryRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    SearchUtils<Salary> searchUtils;
    @Autowired
    private EmployeeProfileRepository employeeProfileRepository;
    @Autowired
    private BonusServiceImpl bonusServiceImpl;
    @Autowired
    private ContractServiceImpl contractServiceImpl;
    @Autowired
    private ContractRepository contractRepository;

    public SalaryServiceImpl(JpaRepository<Salary, Long> repository) {
        super(repository);
        this.salaryRepository = (SalaryRepository) repository;
    }

    @Override
    protected <Req> Salary convertToEntity(Req request) {
        CreateSalaryRequest createSalaryRequest = (CreateSalaryRequest) request;
        // Map các trường từ request vào bản ghi cũ (không tạo mới)
        Salary salary = modelMapper.map(createSalaryRequest, Salary.class);
        // Gán lại thông tin phụ thuộc (nếu có thay đổi)
        salary.setEmployee(
                employeeProfileRepository.findByCode(createSalaryRequest.getEmployeeCode())
                        .orElseThrow(() -> new AppException(ErrorResponse.ENTITY_NOT_FOUND))
        );

        return salary;
    }

    @Override
    protected <Req> Salary convertToEntity(Req request, Salary existingSalary) {
        CreateSalaryRequest createSalaryRequest = (CreateSalaryRequest) request;
        // Map các trường từ request vào bản ghi cũ (không tạo mới)
        modelMapper.map(createSalaryRequest, existingSalary);
        // Gán lại thông tin phụ thuộc (nếu có thay đổi)
        existingSalary.setEmployee(
                employeeProfileRepository.findByCode(createSalaryRequest.getEmployeeCode())
                        .orElseThrow(() -> new AppException(ErrorResponse.ENTITY_NOT_FOUND))
        );

        return existingSalary;
    }


    @Override
    protected <Res> Res convertToResponse(Salary entity, Class<Res> responseType) {
        return modelMapper.map(entity, responseType);
    }

    @Override
    public Long softDelete(Long id) {
        Salary salary = salaryRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorResponse.ENTITY_NOT_FOUND));
        salary.setDeleted(true);
        salaryRepository.save(salary);
        return id;
    }

    @Override
    public BaseSearchResponse<SalaryResponse> search(BaseSearchRequest request, Class<SalaryResponse> responseType) {
        return searchUtils.search(Salary.class,
                request,
                salary -> convertToResponse(salary, responseType));
    }

    @Override
    public List<SalaryResponse> getSalaryByEmployeeId(Long employeeId) {
        List<Salary> salaries = salaryRepository.findByEmployeeId(employeeId);
        return salaries.stream().map(
                salary -> convertToResponse(salary, SalaryResponse.class)
        ).toList();
    }

    public CalculatorSalaryResponse calculateSalary(String employeeCode, String monthYear) {
        EmployeeProfile employeeProfile = employeeProfileRepository.findByCode(employeeCode)
                .orElseThrow(() -> new AppException(ErrorResponse.ENTITY_NOT_FOUND));
        Salary salary = salaryRepository.findByEmployeeAndDate(employeeCode, monthYear).orElse(null);
        if(salary != null)
            throw new AppException(ErrorResponse.ENTITY_ALREADY_EXISTS);
        CalculatorSalaryResponse calculatorSalaryResponse = new CalculatorSalaryResponse();
        calculatorSalaryResponse.setEmployeeCode(employeeCode);
        calculatorSalaryResponse.setTotalBonus(bonusServiceImpl.calculaorBonusByEmployeeAndDate(employeeCode, monthYear));
        calculatorSalaryResponse.setSalary(calcSalaryByContract(employeeProfile.getId()));
        calculatorSalaryResponse.setMonthYear(monthYear);
        return calculatorSalaryResponse;
    }

    @Override
    public Object findAll() {
        List<Salary> list = salaryRepository.findSalariesByDeletedFalse();
        return list.stream()
                .map(salary -> modelMapper.map(salary, SalaryResponse.class))
                .toList();
    }

    public BigDecimal calcSalaryByContract(Long employeeId){
        List<Contract> contracts = contractRepository.findByEmployeeId(employeeId);
        return BigDecimal.valueOf(contracts.stream()
                .mapToDouble(contract -> contract.getMonthlySalary().doubleValue())
                .sum());
    }
}
