package com.example.employee.repository;

import com.example.employee.model.Bonus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.util.List;

public interface BonusRepository extends JpaRepository<Bonus, Long> {
    List<Bonus> findByEmployeeId(Long employeeId);
}
