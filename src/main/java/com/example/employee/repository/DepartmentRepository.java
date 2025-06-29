package com.example.employee.repository;

import com.example.employee.model.Department;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface DepartmentRepository extends JpaRepository<Department, Long> {
    Optional<Department> findDepartmentByName(String itDepartment);

    @Query("SELECT d FROM Department d WHERE d.isDeleted = false")
    List<Department> findDepartmentsByDeletedFalse();
    // Additional query methods can be defined here if needed
}
