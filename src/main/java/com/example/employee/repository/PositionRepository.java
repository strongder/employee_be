package com.example.employee.repository;

import com.example.employee.model.Position;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PositionRepository extends JpaRepository<Position, Long> {
    Optional<Position> findPositionByName(String manager);
}
