package com.kawahedukasi.controller;

import com.kawahedukasi.model.oas.CreateMahasiswaOAS;
import com.kawahedukasi.service.MahasiswaService;
import io.vertx.core.json.JsonObject;
import org.eclipse.microprofile.openapi.annotations.Operation;
import org.eclipse.microprofile.openapi.annotations.media.Content;
import org.eclipse.microprofile.openapi.annotations.media.Schema;
import org.eclipse.microprofile.openapi.annotations.parameters.Parameter;
import org.eclipse.microprofile.openapi.annotations.parameters.RequestBody;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponse;
import org.eclipse.microprofile.openapi.annotations.responses.APIResponses;

import javax.annotation.security.PermitAll;
import javax.annotation.security.RolesAllowed;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.text.ParseException;

@Path("/mahasiswa")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
@RolesAllowed("Admin")
public class MahasiswaController {

    @Inject
    MahasiswaService mahasiswaService;

    @POST
    @Operation(summary = "Create new Mahasiswa", description = "Digunakan untuk membuat data mahasiswa baru")
    @RequestBody(required = true,
            content = @Content(mediaType = MediaType.APPLICATION_JSON,
            schema = @Schema(implementation = CreateMahasiswaOAS.Body.class)))
    @APIResponses({
            @APIResponse(responseCode = "200",
                    content = @Content(mediaType = MediaType.APPLICATION_JSON,
                        schema = @Schema(implementation = CreateMahasiswaOAS.Body.class))),
            @APIResponse(responseCode = "400",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN,
                        schema = @Schema(example = "BAD_REQUEST")))
    })
    public Response create(JsonObject request) throws ParseException {
        return mahasiswaService.create(request);
    }

    @POST
    @Path("/list")
    public Response list(JsonObject request) throws ParseException {
        return mahasiswaService.list(request);
    }

    @GET
    @Path("/all")
    public Response read(@Parameter(example = "2") @QueryParam("page") Integer page){
        return mahasiswaService.all(page);
    }

    @GET
    @Path("/nim/{nim}")
    public Response read(@PathParam("nim") String nim){
        return mahasiswaService.getByNim(nim);
    }

    @GET
    @Path("/{id}")
    public Response read(@PathParam("id") Long id){
        return mahasiswaService.read(id);
    }

    @PUT
    @Path("/{id}")
    public Response update(@PathParam("id") Long id, JsonObject request) throws ParseException {
        return mahasiswaService.update(id, request);
    }

    @DELETE
    @Path("/{id}")
    public Response delete(@PathParam("id") Long id){
        return mahasiswaService.delete(id);
    }

}
