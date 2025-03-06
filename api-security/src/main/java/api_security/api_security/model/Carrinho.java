package api_security.api_security.model;

import java.util.List;

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
    private List<CarrinhoItem> produtos;

    @OneToOne( cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
        name = "carrinho_user",
        joinColumns = @JoinColumn(name = "id_carrinho"),
        inverseJoinColumns = @JoinColumn(name = "id_user")
    )
    private Users user;


    public Carrinho() {
    }

    public Carrinho(List<CarrinhoItem> produtos, Users user) {
        this.produtos = produtos;
        this.user = user;
    }


    public Long getId() {
        return id;
    }
    public List<CarrinhoItem> getProdutos() {
        return produtos;
    }
    public Users getUser() {
        return user;
    }

    public void setProdutos(List<CarrinhoItem> produtos) {
        this.produtos = produtos;
    }

    public void setUser(Users user) {
        this.user = user;
    }


    

    
}
