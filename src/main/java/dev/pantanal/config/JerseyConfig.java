package dev.pantanal.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.springframework.context.annotation.Configuration;

import dev.pantanal.home.HomeController;

@Configuration
public class JerseyConfig extends ResourceConfig {

    public JerseyConfig() {

        register(HomeController.class);
    }
}