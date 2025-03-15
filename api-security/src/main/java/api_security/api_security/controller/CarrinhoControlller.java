package api_security.api_security.controller;

import java.util.List;
import java.util.Optional;

import javax.swing.text.html.parser.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api_security.api_security.model.Carrinho;
import api_security.api_security.model.CarrinhoItem;
import api_security.api_security.model.Produto;
import api_security.api_security.repository.CarrinhoItemRepository;
import api_security.api_security.repository.CarrinhoRepository;
import api_security.api_security.repository.ProdutoRepository;
import api_security.api_security.repository.UsersRepository;
import api_security.api_security.service.ServiceCarrinho;
import api_security.api_security.user.Users;
import api_security.api_security.user.dto.ProdutoDto;
import jakarta.annotation.security.RolesAllowed;
import jakarta.validation.Valid;

@RestController
@RequestMapping("/carrinho")
public class CarrinhoControlller {
    
    @Autowired
    private CarrinhoRepository carrinhoRepository;
    @Autowired
    private ServiceCarrinho service;



    @GetMapping("/list")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<Carrinho> listarCarrinhos() {

        return carrinhoRepository.findAll();

    }

    
    @GetMapping("/")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public Carrinho listarCarrinhosUsuario(JwtAuthenticationToken token) {

        return carrinhoRepository.findByUserId(Integer.parseInt(token.getName()));

    }


    // Adicionar produto ao carrinho
    
    @PostMapping("/adicionar")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity adicionarProduto(@RequestBody @Valid ProdutoDto produtoDto, JwtAuthenticationToken token) {

        return service.adicionarAoCarrinho(produtoDto, token);

    }

    
    @PutMapping("/{produtoId}")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity deletarItem(@PathVariable Integer produtoId, JwtAuthenticationToken token) {
        
       return service.deletarItem(produtoId, token);
    }


    
    @PutMapping("/sub")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity diminuirQtd(@RequestBody ProdutoDto produtoDto, JwtAuthenticationToken token) {

        return service.diminuirQtd(produtoDto, token);

    }


    @PutMapping("/add")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity adicionarQtd(@RequestBody ProdutoDto produtoDto, JwtAuthenticationToken token) {

        return service.adicionarQtd(produtoDto, token);
        
    }

}
