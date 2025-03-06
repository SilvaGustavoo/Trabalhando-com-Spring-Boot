package api_security.api_security.controller;

import java.util.List;
import java.util.Optional;

import javax.swing.text.html.parser.Entity;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
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
import io.swagger.v3.oas.annotations.parameters.RequestBody;

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



    @GetMapping("")
    public List<Carrinho> ListarCarrinhos() {
        return carrinhoRepository.findAll();
    }

    @GetMapping("")
    public List<Carrinho> ListarCarrinhs() {
        return carrinhoRepository.findAll();
    }


    // Adicionar produto ao carrinho
    @PostMapping("/adicionar/")
    public ResponseEntity<CarrinhoItem> adicionarProduto( @RequestBody ProdutoDto produtoDto, JwtAuthenticationToken token) {

        Optional<Produto> produto = produtoRepository.findById(produtoDto.produtoId());

        // se o produto não existir ele joga um erro
        if (produto.isEmpty()) {
            throw new RuntimeException("Produto not found'");
        } else {
            // coleta o carrinho do Usuario
            Optional<Carrinho> carrinhoUsuario = carrinhoRepository.findById(Long.parseLong(token.getName()));
            // coleta o usuario que está adicionando o produto
            Optional<Users> user = usersRepository.findById(Integer.parseInt(token.getName()));

            // adiciona o produto de acordo com os dados necessário, ex: quantidade, preço quando haver cupons, ou quaisquer alterações necessárias para quando o usuario compre

            CarrinhoItem carrinhoProduto = new CarrinhoItem(carrinhoUsuario.get(),produto.get(), produtoDto.quantidade());

            if(carrinhoUsuario.isPresent()) {
                carrinhoUsuario.get().getProdutos().add(carrinhoProduto);
                carrinhoRepository.save(carrinhoUsuario.get());
            } else {
                throw new RuntimeException("User not found");
            }

            return ResponseEntity.ok(carrinhoProduto);

        }
    }

    @DeleteMapping("/")
    public ResponseEntity<List<CarrinhoItem>> deletarItem(@RequestBody Integer produtoId, JwtAuthenticationToken token) {
        
        Carrinho carrinho = carrinhoRepository.findByUserId(Integer.parseInt(token.getName()));

        List<CarrinhoItem> items = carrinho.getProdutos().stream().filter(a -> a.getId().equals(produtoId)).toList();

        carrinho.getProdutos().removeAll(items);

        carrinhoRepository.save(carrinho);

        return ResponseEntity.ok(items);
    }

}
