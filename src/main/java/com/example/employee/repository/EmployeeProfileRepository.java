package com.example.employee.repository;

import com.example.employee.model.Account;
import com.example.employee.model.EmployeeProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface EmployeeProfileRepository extends JpaRepository<EmployeeProfile, Long> {

    @Query("SELECT COUNT(e) FROM EmployeeProfile e WHERE YEAR(e.createdAt) = :year")
    int countByMonth(int year);
    Optional<EmployeeProfile> findByCode(String code);

    @Query("SELECT e FROM EmployeeProfile e WHERE e.isDeleted = false")
    List<EmployeeProfile> findEmployeeProfilesByDeleteFalse();
}
