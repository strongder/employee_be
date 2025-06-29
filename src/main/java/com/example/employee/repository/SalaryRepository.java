package com.example.employee.repository;

import com.example.employee.model.Salary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface SalaryRepository extends JpaRepository<Salary, Long> {

    List<Salary> findByEmployeeId(Long employeeId);

    @Query("SELECT s FROM Salary s WHERE s.employee.code = ?1 AND s.monthYear = ?2")
    Optional<Salary> findByEmployeeAndDate(String employeeCode, String monthYear);

    @Query("SELECT s FROM Salary s WHERE s.isDeleted = false")
    List<Salary> findSalariesByDeletedFalse();
}
