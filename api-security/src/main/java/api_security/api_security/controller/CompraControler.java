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
import api_security.api_security.user.dto.CompraDto;

@RestController
@RequestMapping("/compra")
public class CompraControler {

    @Autowired
    private CompraRepository compraRepository;
    @Autowired
    private CarrinhoRepository carrinhoRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    
    // listar compras feitas
    @GetMapping("")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<Compra> listarCompras() {
        return compraRepository.findAll();
    }

    @GetMapping("/{id}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<Compra> listarComprasPorId(@PathVariable Integer id) {
        List<Compra> listCompra = compraRepository.findByCarrinhoUserId(id);
        Iterator<Compra> compraIterator = listCompra.iterator();

        List<Compra> comprasPeloId = new ArrayList<>();

        if(compraIterator == null) {
            return comprasPeloId;
        }

        while (compraIterator.hasNext()) {
            Compra compra = compraIterator.next();
            if (compra.getUser().getId().equals(id)) {
                comprasPeloId.add(compra);
            }
        }

        return comprasPeloId;
    }

    @GetMapping("/")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public List<Compra> listarComprasUser(JwtAuthenticationToken token) {
        List<Compra> listCompra = compraRepository.findByCarrinhoUserId(Integer.parseInt(token.getName()));
        Iterator<Compra> compraIterator = listCompra.iterator();

        List<Compra> comprasPeloId = new ArrayList<>();

        if(compraIterator == null) {
            return comprasPeloId;
        }

        while (compraIterator.hasNext()) {
            Compra compra = compraIterator.next();
            if (compra.getUser().getId() == Integer.parseInt(token.getName())) {
                comprasPeloId.add(compra);
            }
        }

        return comprasPeloId;
    }



    @PostMapping("/pago")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity comprar(JwtAuthenticationToken token) {
        
        Carrinho carrinho = carrinhoRepository.findByUserId(Integer.parseInt(token.getName()));

        Double valorCarrinho = 0.;

        Produto produto = null;

        for (CarrinhoItem items : carrinho.getProdutos()) {
            valorCarrinho += items.getQuantidade() * items.getProduto().getPreco();

            // reduzir a quantidade de produtos

            produto = items.getProduto();

            if (items.getQuantidade() > produto.getQuantidade()) {
                return ResponseEntity.badRequest().body("Quantidade do produto"+ produto.getNome() + " insuficiente");
            }

            produto.setQuantidade(produto.getQuantidade() - items.getQuantidade());

        }

    
        compraRepository.save(new Compra(valorCarrinho, carrinho, Compra.StatusCompra.CONCLUIDA));

        produtoRepository.save(produto);


        return ResponseEntity.ok().body("Compra bem sucedida");
        
    }


    

}
