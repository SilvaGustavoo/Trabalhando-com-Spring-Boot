package api_security.api_security.model;

import java.time.Instant;
import java.util.List;

import org.hibernate.annotations.ManyToAny;

import api_security.api_security.user.Users;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "hist_compras")
public class Compra {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_compra")
    private Integer id;
    @Column(name = "data_compra")
    private Instant data;
    @Column(name = "valor_compra", nullable = false)
    private Double valor;
    @ManyToMany(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(
        name = "produto_compra",
        joinColumns = @JoinColumn(name = "id_compra"),
        inverseJoinColumns = @JoinColumn(name = "id_produto")
    )
    private List<Produto> list_produtos;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(
        name = "compra_user",
        joinColumns = @JoinColumn(name = "id_compra"),
        inverseJoinColumns = @JoinColumn(name = "id_user")
    )
    private Users user;

    public Compra(Double valor, List<Produto> list_produtos, Users user) {
        this.data = Instant.now();
        this.valor = valor;
        this.list_produtos = list_produtos;
        this.user = user;
    }

    

    public Users getUser() {
        return user;
    }


    public void setUser(Users user) {
        this.user = user;
    }



    public Integer getId() {
        return id;
    }

    public Instant getData() {
        return data;
    }

    public Double getValor() {
        return valor;
    }

    public List<Produto> getList_produtos() {
        return list_produtos;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public void setList_produtos(List<Produto> list_produtos) {
        this.list_produtos = list_produtos;
    }

    
}
