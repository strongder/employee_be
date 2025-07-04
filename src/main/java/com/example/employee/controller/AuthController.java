package com.example.employee.controller;

import com.example.employee.model.Account;
import com.example.employee.repository.AccountRepository;
import com.example.employee.utils.JwtUtils;
import com.example.employee.dtos.request.AuthRequest;
import com.example.employee.dtos.response.ApiResponse;
import com.example.employee.dtos.response.AuthResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/api/v1/auth")
@CrossOrigin(origins = "http://localhost:3000")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;
    @Autowired
    private JwtUtils jwtUtils;
    @Autowired
    private AccountRepository accountRepository;

    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        log.info("authRequest: {}", authRequest);
        Account account = accountRepository.findByUsername(authRequest.getUsername())
                .orElseThrow(() -> new RuntimeException("Tài khoản không tồn tại"));
        if(!account.getStatus().equals("ACTIVE") || account.isDeleted()) {
            return new ResponseEntity<>(new AuthResponse("Tài khoản không hoạt động", null), HttpStatus.FORBIDDEN);
        }
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("authentication: {}", authentication.getAuthorities());
        String token = jwtUtils.generateToken(authRequest.getUsername());
        AuthResponse authResponse = new AuthResponse("Đăng nhập thành công", token);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);

    }
}
