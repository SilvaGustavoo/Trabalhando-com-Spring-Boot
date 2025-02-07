package api_security.api_security.model;


import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

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
    @Column(name = "quantidade_produto", nullable = false)
    private Double quantidade;
    

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


    




    

    

}
