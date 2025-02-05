package spring.api.spring_api.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import spring.api.spring_api.handler.BusinessException;
import spring.api.spring_api.model.Usuario;
import spring.api.spring_api.repository.UsuarioRepository;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;


/**
 * Responsavel por implementar os metodos HTTP com o @RestController
 * 
 */
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {
    @Autowired
    private UsuarioRepository repository;

    @GetMapping("")
    public List<Usuario> getUsers() {
        return repository.findAll();
    }

    @GetMapping("/contendo={palavra}")
    public List<Usuario> findByLogin(@PathVariable("palavra") String palavra) {

        return repository.findByLoginContaining(palavra);
    }


    @DeleteMapping("/delete={id}")
    public void delete(@PathVariable("id") Integer id) {

        repository.deleteById(id);

    }

    
    @PostMapping("")
    public void postUser(@RequestBody Usuario usuario) {
        repository.save(usuario);
    }

    @PutMapping("/atualizarUsuario={id}")
    public void putUsuario(@PathVariable("id") Integer id, @RequestBody Usuario usuario)  {

        if(usuario.getLogin() == null || usuario.getPassword() == null) {
            throw new BusinessException("O campo nome ou senha são obrigatórios");

        } else if(repository.findById(id).isEmpty()) {
            throw new BusinessException("Usuário não encontrado");

        } else {
            repository.updateUsuarioById(id, usuario.getLogin(), usuario.getPassword());
        }
    }

}
