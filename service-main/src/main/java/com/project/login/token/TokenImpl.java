package com.project.login.token;

import com.project.model.Account;
import com.project.payload.ErrorDesc;
import com.project.repository.AccountRepository;
import com.project.utils.AdminUtils;
import io.quarkus.elytron.security.common.BcryptUtil;
import io.quarkus.logging.Log;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.RequestScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;

import static jakarta.ws.rs.core.Response.Status.BAD_REQUEST;

@RequestScoped
public class TokenImpl implements TokenService {
    @Inject
    AdminUtils adminUtils;
    @Inject
    AccountRepository accountRepo;
    private final JwtClaimsBuilder jwt = Jwt.claims();

    @Override
    public JsonObject generateToken(String username, String password) {
        JsonObject object = new JsonObject();
        jwt.issuer("jwt-token");
        jwt.subject("computer-management");
        jwt.issuedAt(System.currentTimeMillis() + 300000);
        jwt.expiresAt(System.currentTimeMillis() + 300000);
        Account account = accountRepo.findByUsername(username);

        if (account != null && BcryptUtil.matches(password, account.getPassword())) {
            Log.infof("User: %s Login successfully!", account.getUsername());
            if (account.isAdmin()) {
                jwt.groups("admin");
                object.put("userType", "admin");
            } else {
                jwt.groups("user");
                object.put("userType", "user");
            }
            object.put("expiresAt", 300);
            object.put("token", jwt.sign());
            return object;
        }
        Log.error("Login failed, Account is invalid username or password!!");
        throw new WebApplicationException(Response.status(BAD_REQUEST)
                .entity(adminUtils.statusResponse(BAD_REQUEST.getStatusCode(), ErrorDesc.BAD_FORMAT)).build());
    }
}
