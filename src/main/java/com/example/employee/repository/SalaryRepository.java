package com.example.employee.repository;

import com.example.employee.model.Salary;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface SalaryRepository extends JpaRepository<Salary, Long> {

    List<Salary> findByEmployeeId(Long employeeId);
}
