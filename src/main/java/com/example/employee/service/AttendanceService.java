package com.example.employee.service;

import com.example.employee.dtos.request.CheckInRequest;
import com.example.employee.dtos.response.AttendanceResponse;
import com.example.employee.model.Attendance;
import com.example.employee.model.Department;

import java.util.List;

public interface AttendanceService extends BaseService<Attendance, Long> {
    // Additional methods specific to Department can be added here
    AttendanceResponse checkIn(CheckInRequest request);
    AttendanceResponse checkOut(CheckInRequest request);
    List<AttendanceResponse> getAttendanceByEmployeeId();

    Object findAll();
}
