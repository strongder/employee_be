package com.example.employee.config.security;


import com.example.employee.model.Account;
import com.example.employee.repository.AccountRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class CustomUserServiceDetail implements UserDetailsService {

	@Autowired
    private AccountRepository accountRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<Account> account = accountRepository.findByUsername(username);
        if (account.isPresent()) {
            return new CustomUserDetail(account.get());
        } else {
            throw new RuntimeException("User not found");
        }
    }
}
