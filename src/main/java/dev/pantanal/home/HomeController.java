package dev.pantanal.home;

import java.util.Collections;
import java.util.Map;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping("/")
public class HomeController {

    @GetMapping
    public Map<String, String> home(@RequestParam(value= "nome", defaultValue =  "student") String nome) {
        return Collections.singletonMap("message", String.format("Welcome to your first app, %s!", nome));
    }
    
}
