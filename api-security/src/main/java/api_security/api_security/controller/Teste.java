package api_security.api_security.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;


@RestController
public class Teste {
    
    @GetMapping
    public String getMethodName() {
        return "BEM VINDO AO NOSSO SITE";
    }
    
}
