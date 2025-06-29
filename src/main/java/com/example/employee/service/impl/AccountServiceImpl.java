package com.example.employee.service.impl;

import com.example.employee.dtos.request.BaseSearchRequest;
import com.example.employee.dtos.request.ChangePasswordRequest;
import com.example.employee.dtos.request.CreateAccountRequest;
import com.example.employee.dtos.response.AccountResponse;
import com.example.employee.dtos.response.BaseSearchResponse;
import com.example.employee.exception.AppException;
import com.example.employee.exception.ErrorResponse;
import com.example.employee.model.Account;
import com.example.employee.repository.AccountRepository;
import com.example.employee.repository.EmployeeProfileRepository;
import com.example.employee.service.AccountService;
import com.example.employee.utils.SearchUtils;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AccountServiceImpl extends BaseServiceImpl<Account, Long> implements AccountService {

    @Override
    public AccountResponse lockAccount(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorResponse.ENTITY_NOT_FOUND));
        account.setStatus("INACTIVE");
        accountRepository.save(account);
        return convertToResponse(account, AccountResponse.class);
    }

    @Override
    public AccountResponse unlockAccount(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorResponse.ENTITY_NOT_FOUND));
        account.setStatus("ACTIVE");
        accountRepository.save(account);
        return convertToResponse(account, AccountResponse.class);
    }

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    SearchUtils<Account> searchUtils;
    @Autowired
    private EmployeeProfileRepository employeeProfileRepository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    public AccountServiceImpl(JpaRepository<Account, Long> repository) {
        super(repository);
        this.accountRepository = (AccountRepository) repository;
    }

    @Override
    protected <Req> Account convertToEntity(Req request) {
        return null;
    }

    @Override
    protected <Req> Account convertToEntity(Req request, Account existingAccount) {
        CreateAccountRequest createAccountRequest = (CreateAccountRequest) request;
        modelMapper.map(request, existingAccount);
        existingAccount.setEmployeeProfile(employeeProfileRepository.findByCode(createAccountRequest.getEmployeeCode()).orElseThrow(
                () -> new AppException(ErrorResponse.ENTITY_NOT_FOUND)
        ));
        return existingAccount;
    }

    @Override
    public AccountResponse create(CreateAccountRequest request) {
        Account  account = convertToEntity(request, new Account());
        account.setPasswordHash(passwordEncoder.encode(request.getPassword()));
        return convertToResponse(accountRepository.save(account), AccountResponse.class);
    }



    @Override
    protected <Res> Res convertToResponse(Account entity, Class<Res> responseType) {
        return modelMapper.map(entity, responseType);
    }


    @Override
    public AccountResponse getCurrentAccount() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        Account account = accountRepository.findByUsername(username)
                .orElseThrow(() -> new AppException(ErrorResponse.ENTITY_NOT_FOUND));
        return  convertToResponse(account, AccountResponse.class);
    }

    @Override
    public String changePassword(ChangePasswordRequest request) {
        Account account = accountRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new AppException(ErrorResponse.ENTITY_NOT_FOUND));
        if (!passwordEncoder.matches(request.getCurrentPassword(), account.getPasswordHash())) {
            throw new RuntimeException("Current password is incorrect");
        }
        account.setPasswordHash(passwordEncoder.encode(request.getNewPassword()));
        accountRepository.save(account);
        return "Password changed successfully";
    }

    @Override
    public Long changeActive(Long id, String status) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorResponse.ENTITY_NOT_FOUND));
        account.setStatus(status);
        accountRepository.save(account);
        return id;
    }

    @Override
    public Long softDelete(Long id) {
        Account account = accountRepository.findById(id)
                .orElseThrow(() -> new AppException(ErrorResponse.ENTITY_NOT_FOUND));
        account.setDeleted(true);
        accountRepository.save(account);
        return id;
    }

    public BaseSearchResponse<AccountResponse> search(BaseSearchRequest request, Class<AccountResponse> responseType) {
        return searchUtils.search(Account.class, request, account ->  convertToResponse(account, responseType));
    }

    @Override
    public Object findAll() {
        List<Account> lists = accountRepository.findAccountsByDeletedFalse();
        return lists.stream()
                .map(account -> convertToResponse(account, AccountResponse.class))
                .toList();
    }

}
