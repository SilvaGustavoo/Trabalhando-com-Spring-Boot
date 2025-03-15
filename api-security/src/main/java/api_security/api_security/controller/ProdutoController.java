package api_security.api_security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api_security.api_security.model.Produto;
import api_security.api_security.repository.ProdutoRepository;
import api_security.api_security.repository.UsersRepository;
import api_security.api_security.service.ServiceProduto;
import api_security.api_security.user.Users;
import api_security.api_security.user.dto.ProdutoDto;
import jakarta.validation.Valid;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
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
    private ServiceProduto service;

    @GetMapping
    public List<Produto> listarProdutos() {
        return repository.findAll();
    }

    @PostMapping("/post")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void postProduto(@RequestBody @Valid Produto produto, JwtAuthenticationToken token) {
        Optional<Produto> optProduto = repository.findAll().stream().filter(a -> a.getNome().equals(produto.getNome())).findFirst();

        if(optProduto.isPresent()) {
            ResponseEntity.badRequest().body("Produto ja está Registrado");
        } else {
            optProduto = Optional.of(produto);
            service.adicionarProduto(optProduto.get(), token);
        }

    }


    @PutMapping("/put/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void putProduto(@PathVariable Integer id, @RequestBody Produto produto) {
        
        service.atualizar(id, produto);

    }


    @PutMapping("/put/add/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void putAdicionarQtd(@PathVariable Integer id, @RequestBody ProdutoDto produtoDto) {
        
        service.adicionarQtd(id, produtoDto);

    }


    @PutMapping("/put/sub/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void putDiminuirQtd(@PathVariable Integer id, @RequestBody ProdutoDto produtoDto) {
        
        service.diminuirQtd(id, produtoDto);

    }

    // Só podera deletar o produto, a pessoa quem o criou
    @DeleteMapping("/del/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public void deleteProduto(@PathVariable Integer id) {

        service.deletar(id);

    }




    
    
}
