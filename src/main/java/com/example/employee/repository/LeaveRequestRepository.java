package com.example.employee.repository;

import com.example.employee.model.LeaveRequest;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface LeaveRequestRepository extends JpaRepository<LeaveRequest, Long> {

    List<LeaveRequest> findByEmployeeId(Long employeeId);

    @Query("SELECT lr FROM LeaveRequest lr WHERE lr.isDeleted = false")
    List<LeaveRequest> findLeaveRequestsByDeletedFalse();
}
