package com.project.login;

import com.project.dto.AccountDto;
import com.project.login.token.TokenService;
import io.vertx.core.json.JsonObject;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HeaderParam;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Singleton
@Path("")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class LoginResource {
    @Inject
    TokenService tokenService;

    @POST
    @Path("/register")
    @Transactional
    public Response registerAccount(AccountDto accountDto) {
        return Response.ok(tokenService.register(accountDto)).build();
    }

    @POST
    @Path("/login/token")
    public Response loginTokenAccount(@HeaderParam("username") String username,
                                      @HeaderParam("password") String password) {
        return Response.ok(tokenService.generateToken(username, password)).build();
    }

    @GET
    @Path("/hello")
    public Response hello() {
        JsonObject jsonObject = new JsonObject();
        jsonObject.put("test", "test micro service");
        return Response.ok(jsonObject).build();
    }
}
