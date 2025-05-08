package com.example.employee.utils;

import com.example.employee.dtos.request.BaseSearchRequest;
import com.example.employee.dtos.response.BaseSearchResponse;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.persistence.TypedQuery;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.CriteriaQuery;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Collectors;

@Component
public class SearchUtils<T> {
    @PersistenceContext
    private EntityManager entityManager;

    public <R> BaseSearchResponse<R> search(Class<T> entityClass, BaseSearchRequest searchRequest, Function<T, R> converter) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<T> query = cb.createQuery(entityClass);
        Root<T> root = query.from(entityClass);
        List<Predicate> predicates = new ArrayList<>();

        // Tìm kiếm theo keyword & value
        if (StringUtils.hasText(searchRequest.getKeyword()) && StringUtils.hasText(searchRequest.getValue())) {
            predicates.add(cb.like(root.get(searchRequest.getKeyword()), "%" + searchRequest.getValue() + "%"));
        }
        // Lọc theo ngày (startDate, endDate)
        if (searchRequest.getStartDate() != null) {
            predicates.add(cb.greaterThanOrEqualTo(root.get("createdAt"), searchRequest.getStartDate().atStartOfDay()));
        }
        if (searchRequest.getEndDate() != null) {
            predicates.add(cb.lessThanOrEqualTo(root.get("createdAt"), searchRequest.getEndDate().atTime(23, 59, 59)));
        }
        query.where(cb.and(predicates.toArray(new Predicate[0])));
        // Sắp xếp
        Sort.Direction direction = Sort.Direction.fromString(searchRequest.getSortDir());
        query.orderBy(direction == Sort.Direction.ASC ? cb.asc(root.get(searchRequest.getSortBy())) : cb.desc(root.get(searchRequest.getSortBy())));

        // Phân trang - fix pageIndex (Java start from 0)
        int pageIndex = Math.max(0, searchRequest.getPage() - 1);
        Pageable pageable = PageRequest.of(pageIndex, searchRequest.getSize(), Sort.by(direction, searchRequest.getSortBy()));

        TypedQuery<T> typedQuery = entityManager.createQuery(query);
        typedQuery.setFirstResult((int) pageable.getOffset());
        typedQuery.setMaxResults(pageable.getPageSize());

        List<T> results = typedQuery.getResultList();
        List<R> transformedResults = results.stream().map(converter).collect(Collectors.toList());



        // Tạo metadata (ví dụ: thời gian thực thi)

        BaseSearchResponse.Pagination pagination = new BaseSearchResponse.Pagination(
                searchRequest.getPage(),
                searchRequest.getSize(),
                countTotal(entityClass, searchRequest),
                (int) Math.ceil((double) countTotal(entityClass, searchRequest) / searchRequest.getSize())
        );

        return new BaseSearchResponse<>(transformedResults, pagination);
    }

    private long countTotal(Class<T> entityClass, BaseSearchRequest searchRequest) {
        CriteriaBuilder cb = entityManager.getCriteriaBuilder();
        CriteriaQuery<Long> countQuery = cb.createQuery(Long.class);
        Root<T> root = countQuery.from(entityClass);

        List<Predicate> predicates = new ArrayList<>();
        if (StringUtils.hasText(searchRequest.getKeyword()) && StringUtils.hasText(searchRequest.getValue())) {
            predicates.add(cb.like(root.get(searchRequest.getKeyword()), "%" + searchRequest.getValue() + "%"));
        }
        countQuery.select(cb.count(root)).where(cb.and(predicates.toArray(new Predicate[0])));

        return entityManager.createQuery(countQuery).getSingleResult();
    }
}
