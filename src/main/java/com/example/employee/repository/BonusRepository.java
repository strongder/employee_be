package com.example.employee.repository;

import com.example.employee.model.Bonus;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;

public interface BonusRepository extends JpaRepository<Bonus, Long> {
    List<Bonus> findByEmployeeId(Long employeeId);

    @Query("SELECT b FROM Bonus b WHERE b.employee.code = :employeeCode AND b.bonusDate BETWEEN :startDate AND :endDate")
    List<Bonus> findByEmployeeIdAndBonusMonth(@Param("employeeCode") String employeeCode,
                                              @Param("startDate") LocalDate startDate,
                                              @Param("endDate") LocalDate endDate);

}
