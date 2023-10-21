package com.project.login.token;

import io.vertx.core.json.JsonObject;

public interface TokenService {
    JsonObject generateToken(String account, String password);
}
