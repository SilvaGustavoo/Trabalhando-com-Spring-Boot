package api_security.api_security.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import api_security.api_security.model.CarrinhoItem;

public interface CarrinhoItemRepository extends JpaRepository<CarrinhoItem, Long>{

    CarrinhoItem findByCarrinhoId(Long id);

    
}
