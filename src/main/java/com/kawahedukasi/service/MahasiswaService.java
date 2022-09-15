package com.kawahedukasi.service;

import com.google.common.base.Strings;
import com.kawahedukasi.model.Mahasiswa;
import com.kawahedukasi.util.BasicUtil;
import com.kawahedukasi.util.FormatUtil;
import io.vertx.core.json.JsonObject;

import javax.enterprise.context.ApplicationScoped;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Query;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@ApplicationScoped
public class MahasiswaService {
    SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");

    @Inject
    EntityManager em;

    @Transactional
    public Response create(JsonObject request) throws ParseException {

        String fullName = request.getString("fullName");
        String nim = request.getString("nim");
        String dob = request.getString("dob");
        String pob = request.getString("pob");
        String jurusan = request.getString("jurusan");
        String gender =request.getString("gender");

        if(!FormatUtil.isStandardNameInput(fullName) || !FormatUtil.isNumericInput(nim)||
        !FormatUtil.isStandardDobInput(dob) || !FormatUtil.isStandardAlphabetInput(pob)||
        !FormatUtil.isStandardAlphabetInput(jurusan) || !FormatUtil.isGenderCodeInput(gender)){
            return Response.status(Response.Status.BAD_REQUEST).entity("BAD_REQUEST").build();
        }

        Optional<Mahasiswa> mahasiswaByNim = Mahasiswa.find("nim = ?1", nim).firstResultOptional();
        if(mahasiswaByNim.isPresent()){
            return Response.status(Response.Status.BAD_REQUEST).entity("NIM_ALREADY_REGISTERED").build();
        }

        Mahasiswa mahasiswa = new Mahasiswa();
        //fullname tidak boleh numeric dan special symbol
        mahasiswa.setFullName(fullName);
        //nim harus numeric
        mahasiswa.setNim(nim);
        //dob format dd-MM-yyyy
        mahasiswa.setDob(simpleDateFormat.parse(dob));
        //pob alphabet
        mahasiswa.setPob(pob);
        //jurusan alphabet
        mahasiswa.setJurusan(jurusan);
        //gender code
        mahasiswa.setGender(gender);

        mahasiswa.persist();

        return Response.ok().entity(mahasiswa).build();
    }

    public Response read(Long id){
        Optional<Mahasiswa> mahasiswa = Mahasiswa.findByIdOptional(id);
        if(mahasiswa.isEmpty()){
            return Response.status(Response.Status.BAD_REQUEST).entity("MAHASISWA_NOT_FOUND").build();
        }
        return Response.ok().entity(mahasiswa.get()).build();

    }

    public Response getByNim(String nim){
        Optional<Mahasiswa> mahasiswa = Mahasiswa.find("nim = ?1", nim).firstResultOptional();
        if(mahasiswa.isEmpty()){
            return Response.status(Response.Status.BAD_REQUEST).entity("MAHASISWA_NOT_FOUND").build();
        }
        return Response.ok().entity(mahasiswa.get()).build();
    }

    public Response list(JsonObject request){
        Integer limit = request.getInteger("limit");
        Integer offset = request.getInteger("offset");

        String name = request.getString("name");
        String pob = request.getString("pob");
        String jurusan = request.getString("jurusan");

        //implement sql
        StringBuilder sb = new StringBuilder();
        sb.append(" SELECT * FROM mahasiswa ");
        sb.append(" WHERE TRUE ");
        if(!Strings.isNullOrEmpty(name)){
            sb.append(" AND full_name ILIKE :name ");
        }if(!Strings.isNullOrEmpty(pob)){
            sb.append(" AND pob ILIKE :pob ");
        }if(!Strings.isNullOrEmpty(jurusan)){
            sb.append(" AND jurusan ILIKE :jurusan ");
        }

        Query query = em.createNativeQuery(sb.toString(), Mahasiswa.class);

        if(!Strings.isNullOrEmpty(name)){
            query.setParameter("name", "%" + name + "%");
        }if(!Strings.isNullOrEmpty(pob)){
            query.setParameter("pob", pob);
        }if(!Strings.isNullOrEmpty(jurusan)){
            query.setParameter("jurusan", jurusan);
        }

        query.setFirstResult(offset);
        query.setMaxResults(limit);

        List<Mahasiswa> resultList = query.getResultList();

        return Response.ok().entity(resultList).build();
    }

    public Response all(Integer page){
        //Pagination
        List<Mahasiswa> data = new ArrayList<>();
        data = Mahasiswa.findAll().page(page, 10).list();
        //result = Mahasiswa.findAll().range(0, 10).list();

        JsonObject result = new JsonObject();
        result.put("data", data);
        result.put("totalPage", BasicUtil.roundUp(Mahasiswa.count(), 10));
        return Response.ok().entity(result).build();
    }

    @Transactional
    public Response update(Long id, JsonObject request) throws ParseException {
        Optional<Mahasiswa> mahasiswaOptional = Mahasiswa.findByIdOptional(id);
        if(mahasiswaOptional.isEmpty()){
            return Response.status(Response.Status.BAD_REQUEST).entity("MAHASISWA_NOT_FOUND").build();
        }

        Mahasiswa mahasiswa = mahasiswaOptional.get();
        mahasiswa.setFullName(request.getString("fullName"));
        mahasiswa.setNim(request.getString("nim"));
        mahasiswa.setDob(simpleDateFormat.parse(request.getString("dob")));
        mahasiswa.setPob(request.getString("pob"));
        mahasiswa.setJurusan(request.getString("jurusan"));
        mahasiswa.setGender(request.getString("gender"));

        mahasiswa.persist();

        return Response.ok().entity(mahasiswa).build();
    }

    @Transactional
    public Response delete(Long id){
        Optional<Mahasiswa> mahasiswaOptional = Mahasiswa.findByIdOptional(id);
        if(mahasiswaOptional.isEmpty()){
            return Response.status(Response.Status.BAD_REQUEST).entity("MAHASISWA_ALREADY_DELETED").build();
        }

        Mahasiswa mahasiswa = mahasiswaOptional.get();
        mahasiswa.delete();

        return Response.ok().build();
    }
}
