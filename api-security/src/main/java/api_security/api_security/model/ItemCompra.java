package api_security.api_security.model;


import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;

@Entity
public class ItemCompra {
    
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id_carrinhoItem")
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "Id_produto")
    private Produto produto;

    @Column(name= "qtd_comprada", nullable = false)
    private Double qtdComprada;

    public ItemCompra(Produto produto, Double qtdComprada) {
        this.produto = produto;
        this.qtdComprada = qtdComprada;
    }

    protected ItemCompra() {
    }

    public static ItemCompra parseItemCompra(CarrinhoItem item) {
        return new ItemCompra(item.getProduto(), item.getQuantidade());
    }
    
    public static Set<ItemCompra> parseItemCompra(Collection<CarrinhoItem> item) {
        Set<ItemCompra> listItem = new HashSet<>();
        for (CarrinhoItem carrinhoItem : item) {
            listItem.add(parseItemCompra(carrinhoItem));
        }
        return listItem;
    }

    public Long getId() {
        return id;
    }

    public Produto getProduto() {
        return produto;
    }

    public Double getQuantidade() {
        return qtdComprada;
    }

    public void setProduto(Produto produto) {
        this.produto = produto;
    }

    public void setQuantidade(Double qtdComprada) {
        this.qtdComprada = qtdComprada;
    }

    
    

}
