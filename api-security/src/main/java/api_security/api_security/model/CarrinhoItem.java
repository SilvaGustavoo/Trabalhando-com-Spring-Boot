package api_security.api_security.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class CarrinhoItem {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_carrinhoItem")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Id_carrinho")
    @JsonIgnore
    private Carrinho carrinho;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Id_produto")
    private Produto produto;

    @Column(name= "qtd_carrinho", nullable = false)
    private Double quantidade;

    public CarrinhoItem(Carrinho carrinho, Produto produto, Double quantidade) {
        this.carrinho = carrinho;
        this.produto = produto;
        this.quantidade = quantidade;
    }

    protected CarrinhoItem() {
    }



    public Long getId() {
        return id;
    }

    public Carrinho getCarrinho() {
        return carrinho;
    }

    public Produto getProduto() {
        return produto;
    }

    public Double getQuantidade() {
        return quantidade;
    }

    public void setCarrinho(Carrinho carrinho) {
        this.carrinho = carrinho;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public void setQuantidade(Double quantidade) {
        this.quantidade = quantidade;
    }

    public void addQuantidade(Double quantidade) {
        this.quantidade += quantidade;
    }

    public void dimQuantidade(Double quantidade) {
        this.quantidade -= quantidade;
    }




    
}
