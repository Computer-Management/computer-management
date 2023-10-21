package com.project.service.impl;

import com.project.dto.AccountDto;
import com.project.model.Account;
import com.project.payload.ErrorDesc;
import com.project.repository.AccountRepository;
import com.project.service.AccountService;
import com.project.utils.AdminUtils;
import com.project.utils.StringUtils;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.logging.Log;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

import static jakarta.ws.rs.core.Response.Status.BAD_REQUEST;
import static jakarta.ws.rs.core.Response.Status.OK;

@ApplicationScoped
public class AccountServiceImpl implements AccountService {
    @Inject
    AdminUtils adminUtils;

    @Inject
    AccountRepository accountRepo;

    @Override
    public List<AccountDto> getAllAccount(int offset, int limit) {
        List<Account> accounts = accountRepo.getAllWithPagination(offset, adminUtils.getLimit(limit));
        List<AccountDto> accountDtos = new ArrayList<>();
        accounts.forEach(account -> accountDtos.add(adminUtils.modelMapper().map(account, AccountDto.class)));
        Log.infof("Get list account success!!", accountDtos);
        return accountDtos;
    }

    @Override
    public AccountDto getAccountByUserName(String username) {
        if (username == null) {
            Log.error("username is null or empty");
            throw new WebApplicationException(Response.status(BAD_REQUEST)
                    .entity(adminUtils.statusResponse(BAD_REQUEST.getStatusCode(), ErrorDesc.BAD_FORMAT)).build());
        }
        Account account = accountRepo.findByUsername(username);
        if (account == null) {
            Log.errorf("Get account by username %s fail!!", username);
            throw new WebApplicationException(Response.status(BAD_REQUEST)
                    .entity(adminUtils.statusResponse(BAD_REQUEST.getStatusCode(), ErrorDesc.ACCOUNT_NOT_EXISTED)).build());
        }
        return adminUtils.modelMapper().map(accountRepo.findByUsername(username), AccountDto.class);
    }

    @Override
    public AccountDto getAccountByPhoneNumber(String phoneNumber) {
        if (phoneNumber == null) {
            Log.error("phoneNumber is null or empty");
            throw new WebApplicationException(Response.status(BAD_REQUEST)
                    .entity(adminUtils.statusResponse(BAD_REQUEST.getStatusCode(), ErrorDesc.BAD_FORMAT)).build());
        }
        Account account = accountRepo.findByPhoneNumber(phoneNumber);
        if (account == null) {
            Log.errorf("Get account by phoneNumber %s fail!!", phoneNumber);
            throw new WebApplicationException(Response.status(BAD_REQUEST)
                    .entity(adminUtils.statusResponse(BAD_REQUEST.getStatusCode(), ErrorDesc.ACCOUNT_NOT_EXISTED)).build());
        }
        return adminUtils.modelMapper().map(accountRepo.findByPhoneNumber(phoneNumber), AccountDto.class);
    }

    @Override
    public AccountDto addNewAccount(AccountDto accountDto) {
        if (accountRepo.findByUsername(accountDto.getUsername()) != null) {
            Log.errorf("Account %s already existed in system!!", accountDto.getUsername());
            throw new WebApplicationException(Response.status(BAD_REQUEST)
                    .entity(adminUtils.statusResponse(BAD_REQUEST.getStatusCode(), ErrorDesc.ACCOUNT_ALREADY_EXISTED)).build());
        }
        if (!StringUtils.isValidEmail(accountDto.getEmail()) || !StringUtils.validatePhoneNumber(accountDto.getPhoneNumber())) {
            Log.errorf("Email %s invalid or PhoneNumber %s is invalid", accountDto.getEmail(), accountDto.getPhoneNumber());
            throw new WebApplicationException(Response.status(BAD_REQUEST)
                    .entity(adminUtils.statusResponse(BAD_REQUEST.getStatusCode(), ErrorDesc.VALIDATE_EMAIL_MOBILE)).build());
        }
        if (!StringUtils.isValidPersonalId(accountDto.getIdentityNumber())) {
            Log.errorf("IdentityNumber %s invalid", accountDto.getIdentityNumber());
            throw new WebApplicationException(Response.status(BAD_REQUEST)
                    .entity(adminUtils.statusResponse(BAD_REQUEST.getStatusCode(), ErrorDesc.VALIDATE_PERSON_ID)).build());
        }
        accountDto.setPassword(BcryptUtil.bcryptHash(accountDto.getPassword()));
        accountDto.setCreateAt(LocalDateTime.now());
        accountDto.setUpdateAt(LocalDateTime.now());
        Account account = adminUtils.modelMapper().map(accountDto, Account.class);
        try {
            accountRepo.persist(account);
            Log.infof("Create account %s success!!", account.getUsername());
            return adminUtils.modelMapper().map(account, AccountDto.class);
        } catch (Exception e) {
            Log.errorf(" %s invalid", accountDto.getIdentityNumber());
            throw new WebApplicationException(Response.status(BAD_REQUEST)
                    .entity(adminUtils.statusResponse(BAD_REQUEST.getStatusCode(), ErrorDesc.BAD_FORMAT)).build());
        }
    }

    @Override
    public AccountDto updateAccount(AccountDto accountDto) {
        if (accountRepo.findByUsername(accountDto.getUsername()) != null) {
            Log.errorf("Account %s already existed in system!!", accountDto.getUsername());
            throw new WebApplicationException(Response.status(BAD_REQUEST)
                    .entity(adminUtils.statusResponse(BAD_REQUEST.getStatusCode(), ErrorDesc.ACCOUNT_ALREADY_EXISTED)).build());
        }
        if (accountDto.getEmail() != null
                && accountRepo.findByEmail(accountDto.getEmail()) != null
                && !StringUtils.isValidEmail(accountDto.getEmail())) {
            Log.errorf("Email %s invalid", accountDto.getEmail());
            throw new WebApplicationException(Response.status(BAD_REQUEST)
                    .entity(adminUtils.statusResponse(BAD_REQUEST.getStatusCode(), ErrorDesc.VALIDATE_EMAIL_MOBILE)).build());
        }
        if (accountDto.getPhoneNumber() != null && !StringUtils.isValidEmail(accountDto.getPhoneNumber())) {
            Log.errorf("PhoneNumber %s is invalid", accountDto.getPhoneNumber());
            throw new WebApplicationException(Response.status(BAD_REQUEST)
                    .entity(adminUtils.statusResponse(BAD_REQUEST.getStatusCode(), ErrorDesc.VALIDATE_EMAIL_MOBILE)).build());
        }
        if (accountDto.getIdentityNumber() != null && !StringUtils.isValidPersonalId(accountDto.getIdentityNumber())) {
            Log.errorf("IdentityNumber %s invalid", accountDto.getIdentityNumber());
            throw new WebApplicationException(Response.status(BAD_REQUEST)
                    .entity(adminUtils.statusResponse(BAD_REQUEST.getStatusCode(), ErrorDesc.VALIDATE_PERSON_ID)).build());
        }
        if (accountDto.getPassword() != null) {
            accountDto.setPassword(BcryptUtil.bcryptHash(accountDto.getPassword()));
        }
        accountDto.setUpdateAt(LocalDateTime.now());
        Account account = adminUtils.modelMapper().map(accountDto, Account.class);
        try {
            accountRepo.persist(account);
            Log.infof("Update account %s success!!", account.getUsername());
            return adminUtils.modelMapper().map(account, AccountDto.class);
        } catch (Exception e) {
            Log.errorf(" %s invalid", accountDto.getIdentityNumber());
            throw new WebApplicationException(Response.status(BAD_REQUEST)
                    .entity(adminUtils.statusResponse(BAD_REQUEST.getStatusCode(), ErrorDesc.BAD_FORMAT)).build());
        }
    }

    @Override
    public Response removeAccount(String username) {
        Account account = accountRepo.findByUsername(username);
        if (account == null) {
            throw new WebApplicationException(Response.status(BAD_REQUEST)
                    .entity(adminUtils.statusResponse(BAD_REQUEST.getStatusCode(), ErrorDesc.ACCOUNT_NOT_EXISTED)).build());
        }
        accountRepo.delete(account);
        Log.infof("Delete account %s success!!", username);
        return Response.ok(adminUtils.statusResponse(OK.getStatusCode(), String.format("Xoá tài khoản %s thành công!", username))).build();
    }
}
