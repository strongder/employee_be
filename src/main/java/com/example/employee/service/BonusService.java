package com.example.employee.service;

import com.example.employee.dtos.request.BaseSearchRequest;
import com.example.employee.dtos.response.BonusResponse;
import com.example.employee.model.Bonus;

import java.util.List;

public interface BonusService extends BaseService<Bonus, Long> {

     Object search(BaseSearchRequest request, Class<BonusResponse> bonusResponseClass);

     List<BonusResponse> getBonusByEmployeeId(Long employeeId);
     Object findAll();
}
