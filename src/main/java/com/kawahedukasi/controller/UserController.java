package com.kawahedukasi.controller;

import com.kawahedukasi.service.UserService;
import io.vertx.core.json.JsonObject;

import javax.annotation.security.PermitAll;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/user")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@PermitAll
public class UserController {

    @Inject
    UserService userService;

    @Path("/login")
    @POST
    public Response login(JsonObject request){
        return userService.login(request);
    }

    @Path("/register")
    @POST
    public Response register(JsonObject request) {
        return userService.register(request);
    }

}
