package com.kawahedukasi;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.kawahedukasi.controller.UserController;
import com.kawahedukasi.model.User;
import io.quarkus.hibernate.orm.panache.PanacheQuery;
import io.quarkus.panache.mock.PanacheMock;
import io.quarkus.test.junit.QuarkusTest;
import io.vertx.core.json.JsonObject;
import org.jose4j.jwk.Use;
import org.junit.jupiter.api.*;
import org.mockito.Mockito;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.core.Response;
import java.util.Optional;

@QuarkusTest
@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class UserTest {

    @Inject
    UserController userController;

    ObjectMapper om = new ObjectMapper();

    User dummyUser(){
        User user = new User();
        user.setRole("Admin");
        user.setName("Ananda");
        user.setUsername("anandadp");
        user.setPassword("Semangat123");
        return user;
    }

    @Test
    @Order(1)
    void login(){
        PanacheMock.mock(User.class);
        PanacheQuery panacheQuery = Mockito.mock(PanacheQuery.class);
        Mockito.when(User.find("username = ?1", "anandadp")).thenReturn(panacheQuery);
        Mockito.when(panacheQuery.firstResultOptional()).thenReturn(Optional.of(dummyUser()));

        JsonObject request = new JsonObject();
        request.put("username", "anandadp");
        request.put("password", "Semangat123");

        Assertions.assertDoesNotThrow(() -> {
            Response response = userController.login(request);

            //check status
            Assertions.assertEquals(Response.Status.OK.getStatusCode(), response.getStatus());

            //check key response
            JsonObject obj = new JsonObject(om.writeValueAsString(response.getEntity()));
            Assertions.assertTrue(obj.containsKey("data"));
            Assertions.assertTrue(obj.containsKey("token"));
        });
    }

    @Test
    @Order(2)
    void loginFailed(){
        PanacheMock.mock(User.class);
        PanacheQuery panacheQuery = Mockito.mock(PanacheQuery.class);
        Mockito.when(User.find("username = ?1", "ananda")).thenReturn(panacheQuery);
        Mockito.when(panacheQuery.firstResultOptional()).thenReturn(Optional.empty());

        JsonObject request = new JsonObject();
        request.put("username", "ananda");
        request.put("password", "Semangat123");

        Assertions.assertDoesNotThrow(() -> {
            Response response = userController.login(request);

            //check status
            Assertions.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());

            Assertions.assertEquals("USER_NOT_FOUND", response.getEntity().toString());
        });

        Mockito.when(User.find("username = ?1", "anandadp")).thenReturn(panacheQuery);
        Mockito.when(panacheQuery.firstResultOptional()).thenReturn(Optional.of(dummyUser()));

        request.put("username", "anandadp");
        request.put("password", "XXX");

        Assertions.assertDoesNotThrow(() -> {
            Response response = userController.login(request);

            //check status
            Assertions.assertEquals(Response.Status.BAD_REQUEST.getStatusCode(), response.getStatus());

            Assertions.assertEquals("AUTH_FAILED", response.getEntity().toString());
        });
    }

    @Test
    @Order(3)
    void register(){
        //membuat method persist() tidak melakukan apapun
        PanacheMock.mock(User.class);
        PanacheMock.doNothing().when(User.class).persist();

        JsonObject request = new JsonObject();
        request.put("username", "ananda");
        request.put("password", "Semangat123");
        request.put("name", "Ananda");
        request.put("role", "Admin");

        Assertions.assertDoesNotThrow(() -> {
            Response response = userController.register(request);
        });
    }
}
