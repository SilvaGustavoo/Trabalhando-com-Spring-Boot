package api_security.api_security.model;


import java.util.Optional;

import org.springframework.boot.autoconfigure.security.SecurityProperties.User;
import org.springframework.http.HttpStatusCode;
import org.springframework.web.server.ResponseStatusException;

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
import jakarta.persistence.ManyToOne;

@Entity
public class Produto {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_produto")
    private Integer id;
    @Column(name = "nome_produto", nullable = false)
    private String nome;
    @Column(name = "descricao_produto")
    private String descricao;
    @Column(name = "preco_produto", nullable = false)
    private Double preco;
    @Column(name = "qtd_produto", nullable = false)
    private Double quantidade;
    @ManyToOne(cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    @JoinTable(
        name = "user_product",
        joinColumns = @JoinColumn(name = "id_produto"),
        inverseJoinColumns = @JoinColumn(name = "id_user")
    )
    private Users user;
    

    public Produto() {
    }

    public Produto(String nome, String descricao, Double preco, Double quantidade) {
        this.nome = nome;
        this.descricao = descricao;
        this.preco = preco;
        this.quantidade = quantidade;
    }





    public Integer getId() {
        return id;
    }


    public String getNome() {
        return nome;
    }


    public String getDescricao() {
        return descricao;
    }


    public Double getPreco() {
        return preco;
    }


    public Double getQuantidade() {
        return quantidade;
    }


    public void setNome(String nome) {
        this.nome = nome;
    }


    public void setDescricao(String descricao) {
        this.descricao = descricao;
    }


    public void setPreco(Double preco) {
        this.preco = preco;
    }


    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public void setUser(Users user) {
        this.user = user;
    }


    




    

    

}
