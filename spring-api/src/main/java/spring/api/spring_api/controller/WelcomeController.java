package spring.api.spring_api.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * O Controller principal que retorna uma mensagem quando acessado o localhost:8080
 */
@RestController
public class WelcomeController {
    @GetMapping
    public String welcome() {
        return "Welcome to the Spring Boot API!";
    }
}
