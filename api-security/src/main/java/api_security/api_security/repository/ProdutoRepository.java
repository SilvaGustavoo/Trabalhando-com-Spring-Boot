package api_security.api_security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import api_security.api_security.model.Produto;

@Repository
public interface ProdutoRepository extends JpaRepository<Produto, Integer> {

    @Modifying
    @Transactional
    @Query("UPDATE Produto u SET u.nome = :nome, u.descricao = :descricao, u.preco = :preco, u.quantidade = :quantidade WHERE u.id = :id")
    public void updateById(Integer id, String nome, String descricao, Double preco, Double quantidade);
}
