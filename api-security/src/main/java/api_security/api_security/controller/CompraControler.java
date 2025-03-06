package api_security.api_security.controller;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.function.BinaryOperator;

import org.springframework.beans.factory.annotation.Autowired;
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
    public List<Compra> listarCompras() {
        return compraRepository.findAll();
    }

    @GetMapping("/{id}")
    public List<Compra> listarComprasPorId(@PathVariable Integer id) {
        Iterator<Compra> compraIterator = compraRepository.findByUserId(id);

        List<Compra> comprasPeloId = new ArrayList<>();

        while (compraIterator.hasNext()) {
            Compra compra = compraIterator.next();
            if (compra.getUser().getId() == id) {
                comprasPeloId.add(compra);
            }
        }

        return comprasPeloId;
    }

    @PostMapping("/pago")
    public void comprar(@PathVariable Integer id, JwtAuthenticationToken token) {
        
        Carrinho carrinho = carrinhoRepository.findByUserId(Integer.parseInt(token.getName()));


        BinaryOperator<Double> soma = (double1, double2) -> double1 + double2;

        Double valorCarrinho = 0.;

        for (CarrinhoItem items : carrinho.getProdutos()) {
            valorCarrinho += items.getQuantidade() * items.getProduto().getPreco();

            // reduzir a quantidade de produtos
            Produto produto = items.getProduto();
            produto.setQuantidade(produto.getQuantidade() - items.getQuantidade());

            produtoRepository.save(produto);

        }

        compraRepository.save(new Compra(valorCarrinho, carrinho.getProdutos(), carrinho.getUser()));

        // reduzir os items do estoque

        
    }


    

}
