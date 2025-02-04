package spring.api.spring_api.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * Utilizado para executar e informar o tipo de retorno dos metodos e transformados para HTTP, possui um uso semelhante do JPARepository, trazendo as funcionalidades e integrações com programas Web.
 * 
 * Ele oferece os metodos padrões para requisições HTTP o Get, Request, Delete, Post e Put.
 */
@RestController
public class WelcomeController {
    @GetMapping
    public String welcome() {
        return "Welcome to the Spring Boot API!";
    }
}
