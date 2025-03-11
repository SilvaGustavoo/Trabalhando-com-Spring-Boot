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
    private CarrinhoItemRepository itemRepository;
    @Autowired
    private ProdutoRepository produtoRepository;
    @Autowired
    private UsersRepository usersRepository;



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
    
    @PostMapping("/adicionar/")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<CarrinhoItem> adicionarProduto(@RequestBody @Valid ProdutoDto produtoDto, JwtAuthenticationToken token) {

        Optional<Produto> produto = produtoRepository.findById(produtoDto.produtoId());

        // se o produto não existir ele joga um erro
        if (produto.isEmpty()) {
            throw new RuntimeException("Produto not found'");
        } else {
            // coleta o carrinho do Usuario
            Carrinho carrinhoUsuario = carrinhoRepository.findByUserId(Integer.parseInt(token.getName()));
            // coleta o usuario que está adicionando o produto
            Optional<Users> user = usersRepository.findById(Integer.parseInt(token.getName()));

            // adiciona o produto de acordo com os dados necessário, ex: quantidade, preço quando haver cupons, ou quaisquer alterações necessárias para quando o usuario compre

            CarrinhoItem carrinhoItem = null;



            if(carrinhoUsuario != null) {
                carrinhoItem = new CarrinhoItem(carrinhoUsuario,produto.get(), produtoDto.quantidade());

                List<CarrinhoItem> produtoNoCarrinho = carrinhoUsuario.getProdutos().stream().filter(a -> a.getId().equals(produto.get().getId())).toList();

                if (produtoNoCarrinho.size() == 0) {
                    carrinhoUsuario.getProdutos().add(carrinhoItem);
                    carrinhoRepository.save(carrinhoUsuario);
                } else {
                    carrinhoUsuario.getProdutos().stream().filter(a -> a.getId().equals(produto.get().getId())).forEach(a -> a.addQuantidade(produtoDto.quantidade()));
                    carrinhoRepository.save(carrinhoUsuario);
                }
                
            } else {
                throw new RuntimeException("User not found");
            }

            return ResponseEntity.ok(carrinhoItem);

        }
    }

    
    @DeleteMapping("/{produtoId}")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<CarrinhoItem> deletarItem(@PathVariable Integer produtoId, JwtAuthenticationToken token) {
        
        Carrinho carrinho = carrinhoRepository.findByUserId(Integer.parseInt(token.getName()));

        Optional<CarrinhoItem> item = carrinho.findProdutoById(Long.valueOf(produtoId));

        if (item.isEmpty()) {
            throw new RuntimeException("Item not found");
        }

        carrinho.getProdutos().remove(item.get());
        carrinhoRepository.save(carrinho);

        return ResponseEntity.ok(item.get());
    }


    
    @DeleteMapping("/")
    @PreAuthorize("hasAuthority('SCOPE_USER')")
    public ResponseEntity<CarrinhoItem> diminuirQtd(@RequestBody ProdutoDto produtoDto, JwtAuthenticationToken token) {


        Carrinho carrinho = carrinhoRepository.findByUserId(Integer.parseInt(token.getName()));

        Optional<CarrinhoItem> items = carrinho.findProdutoById(Long.valueOf(produtoDto.produtoId()));

        if(items.isEmpty()) {
            throw new RuntimeException("Item not found");
        } else {
            if (produtoDto.quantidade() > items.get().getQuantidade()) {
                carrinho.removerProduto(produtoDto.produtoId());
            } else {
                items.get().dimQuantidade(produtoDto.quantidade());
            }
            
        }

        
        carrinhoRepository.save(carrinho);

        return ResponseEntity.ok(items.get());
    }

}
