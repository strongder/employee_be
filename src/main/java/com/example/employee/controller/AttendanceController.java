package com.example.employee.controller;

import com.example.employee.dtos.request.AttendanceRequest;
import com.example.employee.dtos.request.CheckInRequest;
import com.example.employee.dtos.response.ApiResponse;
import com.example.employee.dtos.response.AttendanceResponse;
import com.example.employee.service.AttendanceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/attendance")
public class AttendanceController {

    @Autowired
    AttendanceService attendanceService;

    @GetMapping("/all")
    public ApiResponse getAllAttendance() {
        return ApiResponse.success(attendanceService.findAll());
    }

    @PostMapping("/create")
    public ApiResponse createAttendance(@RequestBody AttendanceRequest request) {
        return ApiResponse.success(attendanceService.save(request, AttendanceResponse.class));
    }

    @PostMapping("/checkin")
    public ApiResponse checkIn(@RequestBody CheckInRequest request) {
        return ApiResponse.success(attendanceService.checkIn(request));
    }

    @PostMapping("/checkout")
    public ApiResponse checkOut(@RequestBody CheckInRequest request) {
        return ApiResponse.success(attendanceService.checkOut(request));
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse deleteAttendance(@PathVariable Long id) {
        return ApiResponse.success(attendanceService.softDelete(id));
    }

    @GetMapping("/get-current")
    public ApiResponse getCurrentAttendance() {
        return ApiResponse.success(attendanceService.getAttendanceByEmployeeId());
    }

}
