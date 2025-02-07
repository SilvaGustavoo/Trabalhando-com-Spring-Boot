package api_security.api_security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api_security.api_security.model.Produto;
import api_security.api_security.repository.ProdutoRepository;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
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

    @GetMapping
    public List<Produto> listarProdutos() {
        return repository.findAll();
    }

    @PostMapping("/post")
    public void postMethodName(@RequestBody Produto produto) {
        repository.save(produto);
    }


    @PutMapping("/put={id}")
    public void putMethodName(@PathVariable Integer id, @RequestBody Produto produto) {
        
        Optional<Produto> produtoEncontrado = repository.findById(id);

        if (produtoEncontrado.isEmpty()) {
            throw new RuntimeException("ID not found: " + id);
        } else {
            repository.updateById(id, produto.getNome(), produto.getDescricao(), produto.getPreco(), produto.getQuantidade());
        }
    }


    @DeleteMapping("/del={id}")
    public void deleteMethodName(@PathVariable Integer id) {
        Produto produtoEncontrado = repository.findById(id).orElse(null);

        if (produtoEncontrado == null) {
            throw new RuntimeException("ID not found: " + id);
        } else {
            repository.deleteById(id);
        }

    }
    
    
}
