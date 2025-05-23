package com.example.employee.controller;

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


    @PostMapping("/login")
    public ResponseEntity<AuthResponse> login(@RequestBody AuthRequest authRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(authRequest.getUsername(), authRequest.getPassword())
        );
        SecurityContextHolder.getContext().setAuthentication(authentication);
        log.info("authentication: {}", authentication.getAuthorities());
        String token = jwtUtils.generateToken(authRequest.getUsername());
        AuthResponse authResponse = new AuthResponse("Đăng nhập thành công", token);
        return new ResponseEntity<>(authResponse, HttpStatus.OK);

    }


    @PostMapping("/refresh-token")
    public ResponseEntity<ApiResponse> refreshToken(@RequestBody Map<String, String> body) {
        String refreshToken = body.get("refreshToken");
        String email = jwtUtils.extractAllClaimsRefresh(refreshToken).getSubject();
        boolean isValid = jwtUtils.isValidRefreshToken(refreshToken);
        if (!isValid) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(ApiResponse.builder()
                            .message("Refresh token failed")
                            .build()
                    );
        } else {
            String token = jwtUtils.generateToken(email);
            AuthResponse authResponse = new AuthResponse("Refresh token success", token);
            return ResponseEntity.ok(ApiResponse.builder()
                    .message("Refresh token success")
                    .result(authResponse)
                    .build()
            );
        }
    }
}
