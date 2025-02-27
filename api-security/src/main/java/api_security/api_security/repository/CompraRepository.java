package api_security.api_security.repository;

import java.util.Iterator;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import api_security.api_security.model.Compra;

@Repository
public interface CompraRepository extends JpaRepository<Compra, Integer> {

    Iterator<Compra> findByUserId(Integer usuarioId);
    
}
