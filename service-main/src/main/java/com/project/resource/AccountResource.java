package com.project.resource;

import com.project.dto.AccountDto;
import com.project.service.AccountService;
import com.project.utils.AdminUtils;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.transaction.Transactional;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.DELETE;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.POST;
import jakarta.ws.rs.PUT;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;
import jakarta.ws.rs.core.Response;

@Path("/account")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@Singleton
public class AccountResource {
    @Inject
    AdminUtils adminUtils;
    @Inject
    AccountService accountService;

    @GET
    @Path("/get-all-account/")
    public Response getAllAccount(@QueryParam("offset") int offset,
                                  @QueryParam("limit") int limit) {
        return Response.ok(accountService.getAllAccount(offset, limit)).build();
    }

    @GET
    @Path("/get-account-by-name/")
    public Response getAccountByUserName(@QueryParam("username") String username) {
        return Response.ok(accountService.getAccountByUserName(username)).build();
    }

    @GET
    @Path("/get-account-by-phone/")
    public Response getAccountByPhoneNumber(@QueryParam("phoneNumber") String phoneNumber) {
        return Response.ok(accountService.getAccountByPhoneNumber(phoneNumber)).build();
    }

    @POST
    @RolesAllowed("admin")
    @Path("/add-new-account/")
    @Transactional
    public Response registerAccount(AccountDto accountDto) {
        return Response.ok(accountService.addNewAccount(accountDto)).build();
    }

    @PUT
    @RolesAllowed("admin")
    @Path("/update-account/")
    @Transactional
    public Response updateAccount(AccountDto accountDto) {
        return Response.ok(accountService.updateAccount(accountDto)).build();
    }


    @DELETE
    @Path("/delete/")
    @Transactional
    public Response removeAccount(@QueryParam("username") String username) {
        return accountService.removeAccount(username);
    }
}
