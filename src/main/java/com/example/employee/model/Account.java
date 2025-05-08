package com.example.employee.model;
import com.example.employee.Enum.RoleEnum;
import jakarta.persistence.*;
import lombok.*;


@Entity
@Table(name = "account")
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Account extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String username;
    private String passwordHash;

    private String status;  // active, inactive, locked
    private RoleEnum role;
    @OneToOne
    private EmployeeProfile employeeProfile;

}
