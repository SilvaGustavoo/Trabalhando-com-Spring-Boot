package api_security.api_security.model;

import java.util.List;
import java.util.Optional;
import java.util.Set;

import api_security.api_security.user.Users;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;

@Entity
public class Carrinho {
    

    @Id
    @GeneratedValue( strategy = GenerationType.AUTO )
    @Column(name =  "id_carrinho")
    private Long id;
    @OneToMany(mappedBy = "carrinho", cascade = CascadeType.ALL)
    private Set<CarrinhoItem> produtos;

    @OneToOne(fetch = FetchType.EAGER)
    @JoinTable(
        name = "carrinho_user",
        joinColumns = @JoinColumn(name = "id_carrinho"),
        inverseJoinColumns = @JoinColumn(name = "id_user")
    )
    private Users user;


    protected Carrinho() {
    }

    public Carrinho(Set<CarrinhoItem> produtos, Users user) {
        this.produtos = produtos;
        this.user = user;
    }


    public Optional<CarrinhoItem> findProdutoById(Integer idProduto) {
        return this.produtos.stream().filter(a -> a.getProduto().getId().equals(idProduto)).findFirst();
    }

    public void removerProdutoId(Integer idProduto) {
        // remover o produto com tal id da lista de produtos
        for (CarrinhoItem item : produtos) {
            if (item.getProduto().getId().equals(idProduto)) {
                produtos.remove(item);
            }
        }
    }
    public void removerProduto(CarrinhoItem item) {
       produtos.remove(item);
    }



    public Long getId() {
        return id;
    }
    public Set<CarrinhoItem> getProdutos() {
        return produtos;
    }
    public Users getUser() {
        return user;
    }

    public void setProdutos(Set<CarrinhoItem> produtos) {
        this.produtos = produtos;
    }

    public void setUser(Users user) {
        this.user = user;
    }


    

    
}
