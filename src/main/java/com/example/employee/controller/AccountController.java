package com.example.employee.controller;

import com.example.employee.dtos.request.BaseSearchRequest;
import com.example.employee.dtos.request.ChangePasswordRequest;
import com.example.employee.dtos.request.CreateAccountRequest;
import com.example.employee.dtos.response.AccountResponse;
import com.example.employee.dtos.response.ApiResponse;
import com.example.employee.service.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/accounts")
public class AccountController {

    @Autowired
    private AccountService accountService;

    @PostMapping("/create")
    public ApiResponse create(@RequestBody  CreateAccountRequest request) {
        return  ApiResponse.success(accountService.create(request));
    }

    @DeleteMapping("/delete/{id}")
    public ApiResponse delete(Long id) {
        return ApiResponse.success(accountService.softDelete(id));
    }

    @PutMapping("/update/{id}")
    public ApiResponse update(@PathVariable  Long id, @RequestBody  CreateAccountRequest request) {
        return ApiResponse.success(accountService.update(id, request, AccountResponse.class));
    }

    @PutMapping("/change-active/{id}")
    public ApiResponse changeActive(@PathVariable Long id, @RequestParam String status) {
        return ApiResponse.success(accountService.changeActive(id, status));
    }

    @GetMapping("/current")
    public ApiResponse getCurrentAccount() {
        return ApiResponse.success(accountService.getCurrentAccount());
    }

    @PostMapping("/search")
    public ApiResponse search(@RequestBody BaseSearchRequest request) {
        return ApiResponse.success(accountService.search(request, AccountResponse.class));
    }

    @GetMapping("/all")
    public ApiResponse getAll() {
        return ApiResponse.success(accountService.findAll(AccountResponse.class));
    }

    @PutMapping("/lock/{id}")
    public ApiResponse lockAccount(@PathVariable Long id) {
        return ApiResponse.success(accountService.lockAccount(id));
    }
    @PutMapping("/unlock/{id}")
    public ApiResponse unlockAccount(@PathVariable Long id) {
        return ApiResponse.success(accountService.unlockAccount(id));
    }

    @PutMapping("/change-password")
    public ApiResponse changePassword(@RequestBody ChangePasswordRequest request) {
        return ApiResponse.success(accountService.changePassword(request));
    }


}
