package api_security.api_security.service;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.oauth2.server.resource.authentication.JwtAuthenticationToken;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import api_security.api_security.model.Produto;
import api_security.api_security.repository.ProdutoRepository;
import api_security.api_security.repository.UsersRepository;
import api_security.api_security.user.Users;


@Service
public class ServiceProduto {

    @Autowired
    private ProdutoRepository repository;
    @Autowired
    private UsersRepository usersRepository;
    
    public void deletar(Integer id) {
        Produto produtoEncontrado = repository.findById(id).orElse(null);

        if (produtoEncontrado == null) {
            throw new RuntimeException("ID not found: " + id);
        } else {
            repository.deleteById(id);
        }
    }

    public void atualizar(Integer id, Produto produto) {
        Optional<Produto> produtoEncontrado = repository.findById(id);

        if (produtoEncontrado.isEmpty()) {
            throw new RuntimeException("ID not found: " + id);
        } else {
            repository.updateById(id, produto.getNome(), produto.getDescricao(), produto.getPreco(), produto.getQuantidade());
        }
    }

    public void adicionarProduto(Produto produto, JwtAuthenticationToken token) {
        Optional<Users> user = usersRepository.findById(Integer.parseInt(token.getName()));
        produto.setUser(user.get());
        repository.save(produto);
    }

    public void comprar(Integer id, JwtAuthenticationToken token) {
        
    }
}
