package com.project.resource;

import com.project.client.CallHttpClient;
import com.project.utils.AdminUtils;
import io.vertx.core.json.JsonObject;
import jakarta.annotation.security.RolesAllowed;
import jakarta.inject.Inject;
import jakarta.inject.Singleton;
import jakarta.ws.rs.Consumes;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.HttpMethod;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.core.MediaType;

@Path("/device")
@Singleton
public class DeviceResource {
    @Inject
    AdminUtils adminUtils;
    @Inject
    CallHttpClient httpClient;

    @GET
    @RolesAllowed({"admin"})
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public JsonObject hello() {
        return httpClient.callHttpWithUrl("http://127.0.0.1:8080/hello", HttpMethod.GET, new JsonObject());
    }
}
