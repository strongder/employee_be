package com.example.employee.config;


import com.example.employee.Enum.RoleEnum;
import com.example.employee.model.*;
import com.example.employee.repository.AccountRepository;
import com.example.employee.repository.DepartmentRepository;
import com.example.employee.repository.EmployeeProfileRepository;
import com.example.employee.repository.PositionRepository;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.AccessLevel;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.time.LocalDateTime;
import java.util.List;

@Configuration
@RequiredArgsConstructor
@FieldDefaults(makeFinal = true, level = AccessLevel.PRIVATE)
public class AppInitConfig {

    AccountRepository accountRepository;
    PositionRepository positionRepository;
    DepartmentRepository departmentRepository;
    EmployeeProfileRepository employeeProfileRepository;
    PasswordEncoder passwordEncoder;

    @PostConstruct
    public void initData() {
        initAdminAccount();
    }

    private void initDepartments() {
        if (departmentRepository.count() == 0) {
            List<Department> departments = List.of(
                    new Department(null, "Human Resources", "Phong nhân sự"),
                    new Department(null, "IT Department", "Phòng IT"),
                    new Department(null, "Finance", "Phong tài chính")
            );
            departmentRepository.saveAll(departments);
        }
    }

    private void initPositions() {
        if (positionRepository.count() == 0) {
            List<Position> positions = List.of(
                    new Position(null, "Manager", "Trưởng phòng"),
                    new Position(null, "Staff", "Nhân viên"),
                    new Position(null, "Intern", "Thực tập sinh")
            );
            positionRepository.saveAll(positions);
        }
    }

    private void initAdminAccount() {
        if (accountRepository.findByUsername("admin").isEmpty()) {
            Account admin = new Account();
            admin.setUsername("admin");
            admin.setPasswordHash(passwordEncoder.encode("1")); // {noop} = không mã hóa, chỉ để demo
            admin.setRole(RoleEnum.MANAGER);
            admin.setStatus("ACTIVE");
            admin.setCreatedAt(LocalDateTime.now());

            // Tạo hồ sơ nhân viên cho admin
            EmployeeProfile profile = new EmployeeProfile();
            profile.setFullName("Administrator");
            profile.setEmail("admin@example.com");
            profile.setDob(LocalDateTime.now().toLocalDate());
            profile.setGender("Male");
            profile.setAddress("Company HQ");
            profile.setCode("EMP20250001");

            // Set default Department và Position
            departmentRepository.findDepartmentByName("IT Department").ifPresent(profile::setDepartment);
            positionRepository.findPositionByName("Manager").ifPresent(profile::setPosition);

            profile.setCreatedAt(LocalDateTime.now());
            employeeProfileRepository.save(profile);
            admin.setEmployeeProfile(profile);
            // Lưu tài khoản admin
            accountRepository.save(admin);
            initPositions();
            initDepartments();
        }
    }
}
