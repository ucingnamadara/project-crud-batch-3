package com.kawahedukasi.controller;

import com.kawahedukasi.model.MataKuliah;
import com.kawahedukasi.service.MataKuliahService;
import io.vertx.core.json.JsonObject;

import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/mataKuliah")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class MataKuliahController {

    @Inject
    MataKuliahService mataKuliahService;

    @POST
    @Path("/{mahasiswaId}")
    @RolesAllowed("Admin")
    public Response create(@PathParam("mahasiswaId") Long mahasiswaId, JsonObject request){
        return mataKuliahService.create(mahasiswaId, request);
    }

    @GET
    @Path("/{mahasiswaId}")
    @RolesAllowed("Admin")
    public Response get(@PathParam("mahasiswaId") Long mahasiswaId){
        return mataKuliahService.get(mahasiswaId);
    }
}
