package dev.pantanal.home;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.view.RedirectView;

import io.swagger.v3.oas.annotations.Hidden;
import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;

@RestController
@RequestMapping("/")
@Hidden
@OpenAPIDefinition(info = @Info(title = "Filme Microsserviço", description = "Microsserviço desenvolvido com propósito didático para o módulo 3 (peixe-dourado).", contact = @Contact(name = "Hudson Silva Borges", url = "https://pantanal.dev", email = "hudson.borges@ufms.br")))
public class HomeController {

    @GetMapping
    public RedirectView home() {
        return new RedirectView("swagger-ui");
    }

}
