package com.example.employee.service.impl;

import com.example.employee.dtos.request.BaseSearchRequest;
import com.example.employee.dtos.response.BaseSearchResponse;
import com.example.employee.dtos.response.PositionResponse;
import com.example.employee.exception.AppException;
import com.example.employee.exception.ErrorResponse;
import com.example.employee.model.Position;
import com.example.employee.repository.PositionRepository;
import com.example.employee.service.PositionService;
import com.example.employee.utils.SearchUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Service;


@Service
public class PositionServiceImpl extends BaseServiceImpl<Position, Long> implements PositionService {

    @Autowired
    PositionRepository positionRepository;
    @Autowired
    private ModelMapper modelMapper;
    @Autowired
    private SearchUtils<Position> searchUtils;

    public PositionServiceImpl(JpaRepository<Position, Long> repository) {
        super(repository);
        this.positionRepository = (PositionRepository) repository;
    }


    @Override
    protected <Req> Position convertToEntity(Req request) {
        return modelMapper.map(request, Position.class);
    }

    @Override
    protected <Req> Position convertToEntity(Req request, Position existingEntity) {
        modelMapper.map(request, existingEntity);
        return existingEntity;
    }

    @Override
    protected <Res> Res convertToResponse(Position entity, Class<Res> responseType) {
        return modelMapper.map(entity, responseType);
    }

    @Override
    public Long softDelete(Long id) {
        Position position = positionRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorResponse.ENTITY_NOT_FOUND));
        position.setDeleted(true);
        positionRepository.save(position);
        return id;
    }

    @Override
    public BaseSearchResponse<PositionResponse> search(BaseSearchRequest request, Class<PositionResponse> responseType) {
        return searchUtils.search(Position.class,
                request,
                position -> convertToResponse(position, responseType));
    }
}
