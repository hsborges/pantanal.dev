package dev.pantanal.home;

import org.springframework.stereotype.Controller;

import jakarta.ws.rs.DefaultValue;
import jakarta.ws.rs.GET;
import jakarta.ws.rs.Path;
import jakarta.ws.rs.Produces;
import jakarta.ws.rs.QueryParam;
import jakarta.ws.rs.core.MediaType;

@Controller
@Path("/")
public class HomeController {

    @GET
    @Produces(MediaType.TEXT_PLAIN)
    public String hello(@QueryParam("nome") @DefaultValue("student") String nome) {
        return String.format("Welcome to your first app, %s!", nome);
    }
    
}
