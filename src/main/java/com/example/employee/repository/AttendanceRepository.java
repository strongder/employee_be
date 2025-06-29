package com.example.employee.repository;

import com.example.employee.model.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface AttendanceRepository extends JpaRepository<Attendance, Long> {
    Attendance findByEmployeeIdAndDate(Long employeeId, LocalDate date);

    List<Attendance> findByEmployeeId(Long employeeId);

    @Query("SELECT a FROM Attendance a WHERE a.isDeleted = false")
    List<Attendance> findAttendancesByDeletedFalse();
    // This interface will automatically inherit CRUD operations from JpaRepository
    // You can add custom query methods here if needed
}
