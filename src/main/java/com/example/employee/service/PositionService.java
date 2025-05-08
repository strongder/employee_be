package com.example.employee.service;

import com.example.employee.dtos.request.BaseSearchRequest;
import com.example.employee.dtos.response.PositionResponse;
import com.example.employee.model.Position;

public interface PositionService extends BaseService<Position, Long> {
    Long softDelete(Long id);
    Object search(BaseSearchRequest request, Class<PositionResponse> positionResponseClass);
}
