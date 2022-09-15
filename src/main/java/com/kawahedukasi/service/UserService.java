package com.kawahedukasi.service;

import com.kawahedukasi.model.User;
import com.kawahedukasi.util.JwtUtil;
import io.vertx.core.json.JsonObject;
import org.jose4j.jwk.Use;

import javax.enterprise.context.ApplicationScoped;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.Optional;

@ApplicationScoped
public class UserService {

    public Response login(JsonObject request){
        String username = request.getString("username");
        String password = request.getString("password");

        Optional<User> userOptional = User.find("username = ?1", username).firstResultOptional();
        if(userOptional.isEmpty()){
            return Response.status(Response.Status.BAD_REQUEST).entity("USER_NOT_FOUND").build();
        }

        User user = userOptional.get();
        if(!user.getPassword().equals(password)){
            return Response.status(Response.Status.BAD_REQUEST).entity("AUTH_FAILED").build();
        }

        String token = JwtUtil.generate(user);
        JsonObject response = new JsonObject();
        response.put("data", user);
        response.put("token", token);

        return Response.ok().entity(response.getMap()).build();
    }

    public Response register(JsonObject request){
        String name = request.getString("name");
        String username = request.getString("username");
        String password = request.getString("password");
        String role = request.getString("role");

        User user = persistResponse(name, username, password, role);

        String token = JwtUtil.generate(user);
        JsonObject response = new JsonObject();
        response.put("data", user);
        response.put("token", token);
        return Response.ok().entity(response.getMap()).build();
    }

    @Transactional
    public User persistResponse(String name, String username, String password, String role){
        User user = new User();
        user.setName(name);
        user.setUsername(username);
        user.setPassword(password);
        user.setRole(role);
        User.persist(user);;

        return user;
    }

}
