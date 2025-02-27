package api_security.api_security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import api_security.api_security.model.Carrinho;

@Repository
public interface CarrinhoRepository extends JpaRepository<Carrinho, Long>{

    /**
     * 
     * @param id ID da class Users, id do usuario para encontrar o carrinho do cliente.
     * @return Carrinho pertecente ao ID inserido
     */
    Carrinho findByUserId(int id);
    
}
