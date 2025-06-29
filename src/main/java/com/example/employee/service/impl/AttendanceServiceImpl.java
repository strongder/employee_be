package com.example.employee.service.impl;

import com.example.employee.Enum.AttendanceStatus;
import com.example.employee.dtos.request.AttendanceRequest;
import com.example.employee.dtos.request.CheckInRequest;
import com.example.employee.dtos.response.AttendanceResponse;
import com.example.employee.exception.AppException;
import com.example.employee.exception.ErrorResponse;
import com.example.employee.model.Account;
import com.example.employee.model.Attendance;
import com.example.employee.repository.AccountRepository;
import com.example.employee.repository.AttendanceRepository;
import com.example.employee.repository.EmployeeProfileRepository;
import com.example.employee.service.AttendanceService;
import jakarta.transaction.Transactional;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalTime;
import java.util.List;

@Service
public class AttendanceServiceImpl extends BaseServiceImpl<Attendance, Long> implements AttendanceService {
    @Autowired
    AttendanceRepository attendanceRepository;
    @Autowired
    ModelMapper modelMapper;
    @Autowired
    private EmployeeProfileRepository employeeProfileRepository;
    @Autowired
    private AccountRepository accountRepository;

    public AttendanceServiceImpl(JpaRepository<Attendance, Long> repository) {
        super(repository);
        this.attendanceRepository = (AttendanceRepository) repository;
    }

    @Override
    protected <Req> Attendance convertToEntity(Req request) {
        if (request instanceof AttendanceRequest attendanceRequest) {
            Attendance attendance = modelMapper.map(attendanceRequest, Attendance.class);
            attendance.setEmployee(employeeProfileRepository.findById(attendanceRequest.getEmployeeId())
                    .orElseThrow(() -> new AppException(ErrorResponse.ENTITY_NOT_FOUND)));
            attendance.setDate(attendanceRequest.getDate());
            return attendance;
        }
        return null;
    }

    public AttendanceResponse checkIn(CheckInRequest request) {
        Attendance existAttendance = attendanceRepository.findByEmployeeIdAndDate(request.getEmployeeId(), request.getDate());
        if (existAttendance != null) {
            throw new AppException(ErrorResponse.ENTITY_ALREADY_EXISTS);
        }
        Attendance attendance = new Attendance();
        attendance.setEmployee(employeeProfileRepository.findById(request.getEmployeeId())
                .orElseThrow(() -> new AppException(ErrorResponse.ENTITY_NOT_FOUND)));
        attendance.setCheckIn(LocalTime.now());
        attendance.setDate(request.getDate());
//        check vi du gio checkIn sau 9h la muon
        if (attendance.getCheckIn().isAfter(LocalTime.of(9, 0))) {
            attendance.setStatus(AttendanceStatus.LATE);
        } else {
            attendance.setStatus(AttendanceStatus.PRESENT);
        }
        attendance.setDeleted(false);
        attendance.setWorkHours(0); // Set initial work hours to 0
        attendanceRepository.save(attendance);
        return convertToResponse(attendance, AttendanceResponse.class);
    }

    public AttendanceResponse checkOut(CheckInRequest request) {
        Attendance attendance = attendanceRepository.findByEmployeeIdAndDate(request.getEmployeeId(), request.getDate());
        attendance.setCheckOut(LocalTime.now());
        // Calculate work hours
        if (attendance.getCheckOut() != null && attendance.getCheckIn() != null) {
            attendance.setWorkHours((float) (attendance.getCheckOut().getHour() - attendance.getCheckIn().getHour()));
        }
        // Check if the employee checked out after 5 PM
        if(attendance.getWorkHours() > 9)
            attendance.setStatus(AttendanceStatus.PRESENT);
        else{
            attendance.setStatus(AttendanceStatus.EARLY_LEAVE);
        }
        attendanceRepository.save(attendance);
        return convertToResponse(attendance, AttendanceResponse.class);
    }

    @Override
    protected <Req> Attendance convertToEntity(Req request, Attendance exist) {
        return null;
    }

    @Override
    protected <Res> Res convertToResponse(Attendance entity, Class<Res> responseType) {
        return modelMapper.map(entity, responseType);
    }

    @Override
    public Long softDelete(Long id) {
        Attendance attendance = attendanceRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorResponse.ENTITY_NOT_FOUND));
        attendance.setDeleted(true);
        attendanceRepository.save(attendance);
        return id;
    }


    @Transactional
    public List<AttendanceResponse> getAttendanceByEmployeeId() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorResponse.ENTITY_NOT_FOUND));
        Long employeeId = account.getEmployeeProfile().getId();
        List<Attendance> attendances = attendanceRepository.findByEmployeeId(employeeId);
        return attendances.stream()
                .map(attendance -> modelMapper.map(attendance, AttendanceResponse.class))
                .toList();
    }

    @Override
    public Object findAll() {
        List<Attendance> list = attendanceRepository.findAttendancesByDeletedFalse();
        return list.stream()
                .map(attendance -> modelMapper.map(attendance, AttendanceResponse.class))
                .toList();
    }
}
