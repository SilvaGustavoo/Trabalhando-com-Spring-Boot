package api_security.api_security.service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import api_security.api_security.model.Carrinho;
import api_security.api_security.model.CarrinhoItem;
import api_security.api_security.model.Compra;
import api_security.api_security.model.Produto;
import api_security.api_security.repository.CarrinhoRepository;
import api_security.api_security.repository.CompraRepository;
import api_security.api_security.repository.ProdutoRepository;


@Service
public class ServiceCompra {

    @Autowired
    private CompraRepository compraRepository;
    @Autowired
    private CarrinhoRepository carrinhoRepository;
    @Autowired
    private ProdutoRepository produtoRepository;

    
    public List<Compra> listarComprasPorId(Integer id) {
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


    public ResponseEntity comprar(JwtAuthenticationToken token) {
        
        Carrinho carrinho = carrinhoRepository.findByUserId(Integer.parseInt(token.getName()));

        Double valorCarrinho = 0.;

        Produto produto = null;

        for (CarrinhoItem items : carrinho.getProdutos()) {
            valorCarrinho += items.getQuantidade() * items.getProduto().getPreco();

            // reduzir a quantidade de produtos

            produto = items.getProduto();

            if (items.getQuantidade() > produto.getQuantidade()) {
                return ResponseEntity.badRequest().body("Quantidade do produto '"+ produto.getNome() + "'' insuficiente");
            }

            produto.dimQuantidade(items.getQuantidade());

        }

    
        compraRepository.save(new Compra(valorCarrinho, carrinho, Compra.StatusCompra.CONCLUIDA.name()));

        if (produto != null) {
            produtoRepository.save(produto);
            carrinho.getProdutos().clear();
            carrinhoRepository.save(carrinho);
        }
        


        return ResponseEntity.ok().body("Compra bem sucedida");
        
    }
}
