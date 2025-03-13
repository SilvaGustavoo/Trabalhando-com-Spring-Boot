package api_security.api_security.model;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.util.List;
import java.util.Set;

import org.hibernate.annotations.ManyToAny;

import api_security.api_security.user.Users;
import api_security.api_security.user.dto.CompraDto;
import api_security.api_security.user.dto.ItemDto;
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
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.persistence.Transient;

@Entity
@Table(name = "compras")
public class Compra {
    
    @Id
    @GeneratedValue(strategy = GenerationType.SEQUENCE)
    @Column(name = "id_compra")
    private Integer id;
    @Column(name = "data_compra")
    private Instant data;
    @Column(name = "status", nullable = false)
    private StatusCompra status;
    @Column(name = "valor_compra", nullable = false)
    private Double valor;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinTable(
        name = "carrinho_compra",
        joinColumns = @JoinColumn(name = "id_compra"),
        inverseJoinColumns = @JoinColumn(name = "id_carrinho")
    )
    private Carrinho carrinho;

    @OneToMany(cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    @JoinTable(
        name = "produtos_compra",
        joinColumns = @JoinColumn(name = "id_compra"),
        inverseJoinColumns = @JoinColumn(name = "id_itemCompra")
    )
    private Set<ItemCompra> listProdutos;

    public Compra(Double valor, Carrinho carrinho, StatusCompra status) {
        this.data = ZonedDateTime.now(ZoneId.of("America/Sao_Paulo")).toInstant();
        this.carrinho = carrinho;
        this.valor = valor;
        this.status = status;
        this.listProdutos = ItemCompra.parseItemCompra(carrinho.getProdutos());

    }

    protected Compra() {
    }


    public void setCarrinho(Carrinho carrinho) {
        this.carrinho = carrinho;
    }


    public Users getUser() {
        return carrinho.getUser();
    }

    public Integer getId() {
        return id;
    }

    public Instant getData() {
        return data;
    }

    public void setLocalData(String local) {
        try {
            this.data = data.atZone(ZoneId.of(local)).toInstant();
        } catch (Exception e) {
            throw new RuntimeException("Local horario inserido pe invalido, deve ser inserido o continente/local (America/Los_Angeles)");
        }
    }

    public Double getValor() {
        return valor;
    }

    public Set<ItemCompra> getListProdutos() {
        return listProdutos;
    }

    public void setValor(Double valor) {
        this.valor = valor;
    }

    public void setListProdutos(Set<ItemCompra> listProdutos) {
        this.listProdutos = listProdutos;
    }


    public Carrinho getCarrinho() {
        return carrinho;
    }


    public enum StatusCompra {
        CONCLUIDA("CONCLUIDA"),
        ABANDONADO("ABANDONADO"),
        FALHA_COMPRA("FALHA");

        private final String value;

        private StatusCompra(String value) {
            this.value = value;
        }
    }

    
}
