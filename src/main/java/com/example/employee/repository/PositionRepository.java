package com.example.employee.repository;

import com.example.employee.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position, Long> {
    Optional<Position> findPositionByName(String manager);

    @Query("SELECT p FROM Position p WHERE p.isDeleted = false")
    List<Position> findPositionsByDeletedFalse();
}
