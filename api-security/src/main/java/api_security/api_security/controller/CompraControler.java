package api_security.api_security.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.function.BinaryOperator;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api_security.api_security.model.Carrinho;
import api_security.api_security.model.CarrinhoItem;
import api_security.api_security.model.Compra;
import api_security.api_security.model.Produto;
import api_security.api_security.repository.CarrinhoRepository;
import api_security.api_security.repository.CompraRepository;
import api_security.api_security.repository.ProdutoRepository;
import api_security.api_security.service.ServiceCompra;
import api_security.api_security.user.dto.CompraDto;

@RestController
@RequestMapping("/compra")
public class CompraControler {

    @Autowired
    private CompraRepository compraRepository;
    @Autowired
    private ServiceCompra service;
    
    // listar compras feitas
    @GetMapping("")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<Compra> listarCompras() {
        return compraRepository.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<Compra> listarComprasPorId(@PathVariable Integer id) {

        return service.listarComprasPorId(id);
    }

    @GetMapping("/")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<Compra> listarComprasUser(JwtAuthenticationToken token) {

        return service.listarComprasUser(token);
    }



    @PostMapping("/pago")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity comprar(JwtAuthenticationToken token) {
        
        return service.comprar(token);
        
    }


    

}
