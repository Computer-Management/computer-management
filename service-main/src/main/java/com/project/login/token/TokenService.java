package com.project.login.token;

import com.project.dto.AccountDto;
import io.vertx.core.json.JsonObject;

public interface TokenService {
    AccountDto register(AccountDto accountDto);

    JsonObject generateToken(String account, String password);
}
