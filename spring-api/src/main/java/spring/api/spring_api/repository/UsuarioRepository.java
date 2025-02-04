package spring.api.spring_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import spring.api.spring_api.model.Usuario;
import java.util.List;


@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    public List<Usuario> findByLoginContaining(String palavra);

    public void deleteByLogin(String login);

}
