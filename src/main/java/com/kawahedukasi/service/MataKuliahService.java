package com.kawahedukasi.service;

import com.kawahedukasi.model.Mahasiswa;
import com.kawahedukasi.model.MataKuliah;
import io.vertx.core.json.JsonObject;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.List;

@ApplicationScoped
public class MataKuliahService {

    @Transactional
    public Response create(Long mahasiswaId, JsonObject request){
        Mahasiswa mahasiswa = Mahasiswa.findById(mahasiswaId);
        if(mahasiswa == null){
            return Response.status(Response.Status.BAD_REQUEST).entity("MAHASISWA_NOT_FOUND").build();
        }

        MataKuliah mataKuliah = new MataKuliah();
        mataKuliah.setName(request.getString("name"));
        mataKuliah.setSks(request.getInteger("sks"));
        mataKuliah.setMahasiswa(mahasiswa);
        mataKuliah.persist();

        return Response.ok().build();
    }

    public Response get(Long mahasiswaId){
        List<MataKuliah> mataKuliah = MataKuliah.find("mahasiswa_id = ?1", mahasiswaId).list();
        return Response.ok().entity(mataKuliah).build();
    }
}
