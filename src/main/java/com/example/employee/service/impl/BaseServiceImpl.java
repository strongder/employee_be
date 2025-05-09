package com.example.employee.service.impl;

import com.example.employee.dtos.request.BaseSearchRequest;
import com.example.employee.exception.AppException;
import com.example.employee.exception.ErrorResponse;
import com.example.employee.service.BaseService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public abstract class BaseServiceImpl<T, ID> implements BaseService<T, ID> {
    protected final JpaRepository<T, ID> repository;
    public BaseServiceImpl(JpaRepository<T, ID> repository ) {
        this.repository = repository;
    }

    @Override
    public <Req, Res> Res save(Req request, Class<Res> responseType) {
        T entity = convertToEntity(request);
        repository.save(entity);
        return convertToResponse(entity, responseType);
    }

    public <Req, Res> String saveAll(List<Req> request) {
        List<T> list = request.stream()
                .map(this::convertToEntity)
                .toList();
        repository.saveAll(list);
        return "Success";
    }

    @Override
    public <Req, Res> Res update(ID id, Req request, Class<Res> responseType) {
        // Tìm bản ghi cũ
        T existingEntity = repository.findById(id)
                .orElseThrow(() -> new AppException(ErrorResponse.ENTITY_NOT_FOUND));
        // Cập nhật thông tin từ request vo bản ghi cũ
        convertToEntity(request, existingEntity);
        // Lưu lại vào DB
        T updatedEntity = repository.save(existingEntity);
        // Trả về response
        return convertToResponse(updatedEntity, responseType);
    }

    @Override
    public void delete(ID id) {
        repository.deleteById(id);
    }

    @Override
    public <Res> Res getById(ID id, Class<Res> responseType) {
        T entity = repository.findById(id)
                .orElseThrow(() -> new AppException(ErrorResponse.ENTITY_NOT_FOUND));
        return convertToResponse(entity, responseType);
    }
    @Override
    public T findById(ID id) {
        return repository.findById(id)
                .orElseThrow(() -> new AppException(ErrorResponse.ENTITY_NOT_FOUND));
    }

    @Override
    public <Res> List<Res> findAll(Class<Res> responseType) {
        return repository.findAll().stream()
                .map(entity -> convertToResponse(entity, responseType))
                .toList();
    }

    @Override
    public void save(T entity) {
        repository.save(entity);
    }
    protected abstract <Req> T convertToEntity(Req request);
    protected abstract <Req> T convertToEntity(Req request, T existingEntity);
    protected abstract <Res> Res convertToResponse(T entity, Class<Res> responseType);

    public abstract ID softDelete(ID id);

}
