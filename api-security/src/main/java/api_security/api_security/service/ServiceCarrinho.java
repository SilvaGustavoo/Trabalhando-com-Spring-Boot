package api_security.api_security.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import api_security.api_security.model.Carrinho;
import api_security.api_security.model.CarrinhoItem;
import api_security.api_security.model.Produto;
import api_security.api_security.repository.CarrinhoItemRepository;
import api_security.api_security.repository.CarrinhoRepository;
import api_security.api_security.repository.ProdutoRepository;
import api_security.api_security.user.dto.ProdutoDto;

@Service
public class ServiceCarrinho {

    @Autowired
    private CarrinhoRepository carrinhoRepository;
    @Autowired
    private CarrinhoItemRepository itemRepository;
    @Autowired
    private ProdutoRepository produtoRepository;

    
    public ResponseEntity adicionarAoCarrinho(ProdutoDto produtoDto, JwtAuthenticationToken token) {

        Optional<Produto> produto = produtoRepository.findById(produtoDto.produtoId());

        // Se o produto não existir ele joga um erro
        if (produto.isEmpty()) {
            return ResponseEntity.ok("Produto not found'");
        }

        // coleta o carrinho do Usuario
        Carrinho carrinhoUsuario = carrinhoRepository.findByUserId(Integer.parseInt(token.getName()));

        CarrinhoItem carrinhoItem = null;
        boolean addMax = false;


        // Verifica se o usuario tem um carrinho registrado
        if(carrinhoUsuario == null) {
            throw new RuntimeException("User not found");
        }

    
        carrinhoItem = new CarrinhoItem(carrinhoUsuario,produto.get(), 
                                        produtoDto.quantidade());

        List<CarrinhoItem> produtoNoCarrinho = carrinhoUsuario
                                                .getProdutos()
                                                .stream()
                                                .filter(a -> a.getProduto().getId().equals(produto.get().getId()))
                                                .toList();

        // adiciona um novo produto ou aumenta a quantidade do existente
        if (produtoNoCarrinho.size() == 0) {
            carrinhoUsuario.getProdutos().add(carrinhoItem);
            carrinhoRepository.save(carrinhoUsuario);
        } else {
            for (CarrinhoItem item : carrinhoUsuario.getProdutos()) {
                if (item.getProduto().getId().equals(produto.get().getId())) {

                    item.addQuantidade(produtoDto.quantidade());
                    
                    // Se for tentar adicionar mais do que se existe em estoque, altera para a quantidade total existente
                    if(item.getQuantidade() > produto.get().getQuantidade()) {
                        item.setQuantidade(produto.get().getQuantidade());
                        addMax = true;
                        
                    }
                    carrinhoUsuario.getProdutos().add(item);
                }
            }
            carrinhoRepository.save(carrinhoUsuario);
        }
            

        if (addMax == true) {
            return ResponseEntity.badRequest().body("Adicionou mais do que existe em estoque, quantidade total alterada para " + produto.get().getQuantidade());
        }

        return ResponseEntity.ok(carrinhoItem);

    }


    public ResponseEntity deletarItem(Integer produtoId, JwtAuthenticationToken token) {
        Carrinho carrinho = carrinhoRepository.findByUserId(Integer.parseInt(token.getName()));

        Optional<CarrinhoItem> item = carrinho.findProdutoById(produtoId);

        if (item.isEmpty()) {
            return ResponseEntity.ok("Item not found");
        }

        carrinho.removerProduto(item.get());
        itemRepository.deleteById(item.get().getId());
        carrinhoRepository.save(carrinho);

        return ResponseEntity.ok(item.get());
    }



    public ResponseEntity diminuirQtd(ProdutoDto produtoDto, JwtAuthenticationToken token) {

        Carrinho carrinho = carrinhoRepository.findByUserId(Integer.parseInt(token.getName()));

        Optional<CarrinhoItem> items = carrinho.findProdutoById(produtoDto.produtoId());

        if(items.isEmpty()) {
            return ResponseEntity.badRequest().body("Item not found");
        } else {
            if (produtoDto.quantidade() > items.get().getQuantidade()) {
                carrinho.removerProduto(items.get());
            } else {
                items.get().dimQuantidade(produtoDto.quantidade());
            }
            
        }
        
        carrinhoRepository.save(carrinho);
        return ResponseEntity.ok(items.get());
    }


    public ResponseEntity adicionarQtd(ProdutoDto produtoDto, JwtAuthenticationToken token) {
        
        
        Carrinho carrinho = carrinhoRepository.findByUserId(Integer.parseInt(token.getName()));

        Optional<CarrinhoItem> items = carrinho.findProdutoById(produtoDto.produtoId());

        if(items.isEmpty()) {
            return ResponseEntity.badRequest().body("Item not found");
        } else {
            if (produtoDto.quantidade() > items.get().getQuantidade()) {
                carrinho.removerProduto(items.get());
            } else {
                items.get().addQuantidade(produtoDto.quantidade());
            }
            
        }
        
        carrinhoRepository.save(carrinho);
        return ResponseEntity.ok(items.get());

    }



}
