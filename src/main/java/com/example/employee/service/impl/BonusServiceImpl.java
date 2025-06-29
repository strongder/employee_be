package com.example.employee.service.impl;

import com.example.employee.dtos.request.BaseSearchRequest;
import com.example.employee.dtos.request.CreateBonusRequest;
import com.example.employee.dtos.response.BaseSearchResponse;
import com.example.employee.dtos.response.BonusResponse;
import com.example.employee.exception.AppException;
import com.example.employee.exception.ErrorResponse;
import com.example.employee.model.Bonus;
import com.example.employee.repository.BonusRepository;
import com.example.employee.repository.EmployeeProfileRepository;
import com.example.employee.service.BonusService;
import com.example.employee.utils.SearchUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Service
public class BonusServiceImpl extends BaseServiceImpl<Bonus, Long> implements BonusService {

    @Autowired
    BonusRepository bonusRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private SearchUtils<Bonus> searchUtils;
    @Autowired
    private EmployeeProfileRepository employeeProfileRepository;

    public BonusServiceImpl(JpaRepository<Bonus, Long> repository) {
        super(repository);
        this.bonusRepository = (BonusRepository) repository;
    }
    @Override
    protected <Req> Bonus convertToEntity(Req request) {
        CreateBonusRequest bonusRequest = (CreateBonusRequest) request;
        Bonus bonus = modelMapper.map(bonusRequest, Bonus.class);
        bonus.setEmployee(employeeProfileRepository.findByCode(bonusRequest.getEmployeeCode())
                .orElseThrow(() -> new AppException(ErrorResponse.ENTITY_NOT_FOUND)));
        return bonus;
    }

    @Override
    protected <Req> Bonus convertToEntity(Req request, Bonus existingEntity) {
        CreateBonusRequest bonusRequest = (CreateBonusRequest) request;
        modelMapper.map(bonusRequest, existingEntity);
        existingEntity.setEmployee(employeeProfileRepository.findByCode(bonusRequest.getEmployeeCode())
                .orElseThrow(() -> new AppException(ErrorResponse.ENTITY_NOT_FOUND)));
        return existingEntity;
    }

    @Override
    protected <Res> Res convertToResponse(Bonus entity, Class<Res> responseType) {
        return modelMapper.map(entity, responseType);
    }

    @Override
    public Long softDelete(Long id) {
        Bonus bonus = bonusRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorResponse.ENTITY_NOT_FOUND));
        bonus.setDeleted(true);
        return id;
    }

    @Override
    public BaseSearchResponse<BonusResponse> search(BaseSearchRequest request, Class<BonusResponse> bonusResponseClass) {
        return searchUtils.search(Bonus.class,
                request,
                bonus -> convertToResponse(bonus, bonusResponseClass));
    }

    @Override
    public List<BonusResponse> getBonusByEmployeeId(Long employeeId) {
        List<Bonus> list = bonusRepository.findByEmployeeId(employeeId);
        return list.stream()
                .map(bonus -> convertToResponse(bonus, BonusResponse.class))
                .toList();
    }

    public BigDecimal calculaorBonusByEmployeeAndDate(String employeeCode, String monthYear) {
        LocalDate startDate = LocalDate.parse(monthYear + "-01");
        LocalDate endDate = startDate.withDayOfMonth(startDate.lengthOfMonth());
        List<Bonus> list = bonusRepository.findByEmployeeIdAndBonusMonth(employeeCode, startDate, endDate);
        return list.stream()
                .map(Bonus::getBonusAmount)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    public Object findAll()
    {
        List<Bonus> lists = bonusRepository.findBonusesByDeletedFalse();
        return lists.stream()
                .map(bonus -> convertToResponse(bonus, BonusResponse.class))
                .toList();
    }
}
