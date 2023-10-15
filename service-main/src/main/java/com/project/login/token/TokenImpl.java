package com.project.login.token;

import com.project.dto.AccountDto;
import com.project.model.Account;
import com.project.payload.ErrorDesc;
import com.project.payload.ResultCode;
import com.project.repository.AccountRepository;
import com.project.utils.AdminUtils;
import com.project.utils.StringUtils;
import io.quarkus.logging.Log;
import io.smallrye.jwt.build.Jwt;
import io.smallrye.jwt.build.JwtClaimsBuilder;
import io.vertx.core.json.JsonObject;
import jakarta.enterprise.context.ApplicationScoped;
import jakarta.inject.Inject;
import jakarta.ws.rs.WebApplicationException;
import jakarta.ws.rs.core.Response;
import org.apache.commons.codec.digest.DigestUtils;

import java.time.LocalDateTime;

import static jakarta.ws.rs.core.Response.Status.BAD_REQUEST;

@ApplicationScoped
public class TokenImpl implements TokenService {
    @Inject
    AdminUtils adminUtils;
    @Inject
    AccountRepository accountRepo;
    private final JwtClaimsBuilder jwt = Jwt.claims();

    @Override
    public AccountDto register(AccountDto accountDto) {
        if (Account.findByUsername(accountDto.getUsername()) != null) {
            throw new WebApplicationException(Response.status(BAD_REQUEST).entity(ErrorDesc.ACCOUNT_ALREADY_EXISTED).build());
        }
        if (!StringUtils.isValidEmail(accountDto.getEmail()) || !StringUtils.validatePhoneNumber(accountDto.getPhoneNumber())) {
            throw new WebApplicationException(Response.status(BAD_REQUEST).entity(ErrorDesc.VALIDATE_EMAIL_MOBILE).build());
        }
        if (!StringUtils.isValidPersonalId(accountDto.getIdentityNumber())) {
            throw new WebApplicationException(Response.status(BAD_REQUEST).entity(ErrorDesc.VALIDATE_PERSON_ID).build());
        }
        accountDto.setPassword(DigestUtils.md5Hex(accountDto.getPassword()));
        accountDto.setCreateAt(LocalDateTime.now());
        accountDto.setUpdateAt(LocalDateTime.now());
        Account account = adminUtils.modelMapper().map(accountDto, Account.class);
        try {
            accountRepo.persist(account);
            return adminUtils.modelMapper().map(account, AccountDto.class);
        } catch (Exception e) {
            throw new WebApplicationException(Response.status(BAD_REQUEST).entity(ErrorDesc.ACCOUNT_ALREADY_EXISTED).build());
        }
    }

    @Override
    public JsonObject generateToken(String username, String password) {
        JsonObject object = new JsonObject();
        jwt.issuer("jwt-token");
        jwt.subject("computer-management");
        jwt.expiresAt(System.currentTimeMillis() + 3600);
        object.put("expiresAt", 3600);
        Account account = Account.findByUsername(username);
        if (account != null
                && adminUtils.verifyPassword(password)
                && password.equals(account.getPassword())) {
            Log.infof("User: %s Login successfully!", account.getUsername());
            if (account.isAdmin()) {
                jwt.groups("admin");
                object.put("userType", "admin");
                object.put("token", jwt.sign());
                return object;
            }
            jwt.groups("user");
            object.put("userType", "user");
            object.put("token", jwt.sign());
            return object;
        }
        Log.info("Login failed, Account is invalid username or password!!");
        object = new JsonObject();
        object.put("resultCode", ResultCode.FAIL);
        object.put("message", "Account is invalid username or password!");
        throw new WebApplicationException(Response.status(BAD_REQUEST).entity(object).build());
    }
}
