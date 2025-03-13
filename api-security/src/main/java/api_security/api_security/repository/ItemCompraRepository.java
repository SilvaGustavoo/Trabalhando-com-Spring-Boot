package api_security.api_security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import api_security.api_security.model.ItemCompra;

@Repository
public interface ItemCompraRepository extends JpaRepository<ItemCompra, Long> {
    
}
