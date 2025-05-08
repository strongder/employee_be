package com.example.employee.service;

import com.example.employee.dtos.request.BaseSearchRequest;
import com.example.employee.dtos.request.ChangePasswordRequest;
import com.example.employee.dtos.request.CreateAccountRequest;
import com.example.employee.dtos.response.AccountResponse;
import com.example.employee.dtos.response.BaseSearchResponse;
import com.example.employee.model.Account;

public interface AccountService extends BaseService<Account, Long> {
    AccountResponse create(CreateAccountRequest request);

    AccountResponse getCurrentAccount();
    String changePassword(ChangePasswordRequest request);
    Long changeActive(Long id, String status);
    AccountResponse lockAccount(Long id);
    AccountResponse unlockAccount(Long id);

    Long softDelete(Long id);
    BaseSearchResponse<AccountResponse> search(BaseSearchRequest request, Class<AccountResponse> responseType);
}
