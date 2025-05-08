package com.example.employee.repository;

import com.example.employee.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, Long> {

   List<Contract> findByEmployeeId(Long employeeId);
}
