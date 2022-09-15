package com.kawahedukasi;

import org.eclipse.microprofile.openapi.annotations.OpenAPIDefinition;
import org.eclipse.microprofile.openapi.annotations.info.Contact;
import org.eclipse.microprofile.openapi.annotations.info.Info;
import org.eclipse.microprofile.openapi.annotations.info.License;

import javax.ws.rs.core.Application;

@OpenAPIDefinition(
        info = @Info(
                title="Project CRUD by Kawah Edukasi",
                version = "1.0.0",
                contact = @Contact(
                        name = "Ananda Dana Pratama",
                        url = "http://instagram/anandadp_",
                        email = "ananda-pratama@email.com"),
                license = @License(
                        name = "Kawah Edukasi",
                        url = "http://kawahedukasi.com")))
public class ProjectCRUDApplication extends Application {
}
