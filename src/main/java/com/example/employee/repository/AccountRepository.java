package com.example.employee.repository;

import com.example.employee.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {
    Optional<Account> findByUsername(String username);
    boolean existsByUsername(String username);

    @Query("SELECT a FROM Account a WHERE a.isDeleted = false")
    List<Account> findAccountsByDeletedFalse();
}
