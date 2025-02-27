package api_security.api_security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api_security.api_security.model.Produto;
import api_security.api_security.repository.ProdutoRepository;
import api_security.api_security.repository.UsersRepository;
import api_security.api_security.service.ServiceProduto;
import api_security.api_security.user.Users;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;




@RestController
@RequestMapping("/produtos")
public class ProdutoController {
    
    @Autowired
    private ProdutoRepository repository;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private ServiceProduto service;

    @GetMapping
    public List<Produto> listarProdutos() {
        return repository.findAll();
    }

    @PostMapping("/post")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void postMethodName(@RequestBody Produto produto, JwtAuthenticationToken token) {
        service.adicionarProduto(produto, token);
    }


    @PutMapping("/put={id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void putMethodName(@PathVariable Integer id, @RequestBody Produto produto) {
        
        service.atualizar(id, produto);

    }

    // Só podera deletar o produto, a pessoa quem o criou
    @DeleteMapping("/del={id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void delete(@PathVariable Integer id) {

        service.deletar(id);

    }

    @PutMapping("/{id}/buy")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public void buy(@PathVariable Integer id, JwtAuthenticationToken token) {
        service.comprar(id, token);
    }
    
    
}
