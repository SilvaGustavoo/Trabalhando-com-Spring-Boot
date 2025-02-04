package spring.api.spring_api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RestController;

import jakarta.transaction.Transactional;
import spring.api.spring_api.model.Usuario;
import spring.api.spring_api.repository.UsuarioRepository;
/**
 * Dentro do @GerMapping é sempre necessário inserir um prefixo no quali definirá a localização do retorno da função que no caso foi retornado no 
 * 
 * localhost:8080/usuarios
 */
@RestController
public class UsuarioController {
    @Autowired
    private UsuarioRepository repository;

    @GetMapping("/usuarios")
    public List<Usuario> getUsers() {
        return repository.findAll();
    }

    @GetMapping("/usuarios/contendo={palavra}")
    public List<Usuario> findByLogin(@PathVariable("palavra") String palavra) {

        return repository.findByLoginContaining(palavra);
    }


    @DeleteMapping("/usuarios/delete={id}")
    public void delete(@PathVariable("id") Integer id) {

        repository.deleteById(id);

    }
}
