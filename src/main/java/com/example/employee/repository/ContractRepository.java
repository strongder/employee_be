package com.example.employee.repository;

import com.example.employee.model.Contract;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

public interface ContractRepository extends JpaRepository<Contract, Long> {

    @Query("SELECT c FROM Contract c WHERE c.employee.id = :employeeId AND c.isDeleted = false")
   List<Contract> findByEmployeeId(Long employeeId);

   @Query("SELECT c FROM Contract c WHERE c.isDeleted = false")
    List<Contract> findContractsByDeletedFalse();
    List<Contract> findByEndDateBeforeAndContractStatus(LocalDate today, String active);
}
