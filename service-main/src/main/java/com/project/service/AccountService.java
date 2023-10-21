package com.project.service;

import com.project.dto.AccountDto;
import jakarta.ws.rs.core.Response;

import java.util.List;

public interface AccountService {
    List<AccountDto> getAllAccount(int offset, int limit);

    AccountDto getAccountByUserName(String username);

    AccountDto getAccountByPhoneNumber(String phoneNumber);

    AccountDto addNewAccount(AccountDto account);

    AccountDto updateAccount(AccountDto account);

    Response removeAccount(String username);
}
