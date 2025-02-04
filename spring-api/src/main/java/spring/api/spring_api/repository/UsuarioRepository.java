package spring.api.spring_api.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import spring.api.spring_api.model.Usuario;
import java.util.List;

/**
 * Foi necessário o uso do @Modifying, pois sem ele o JPA entende como padrão como pesquisa GET, e para utilizar qualquer metodo diferente do SELECT tem a necessidade de utilizar o Modifying
 */
@Repository
public interface UsuarioRepository extends JpaRepository<Usuario, Integer> {

    public List<Usuario> findByLoginContaining(String palavra);

    public void deleteByLogin(String login);

    @Modifying
    @Transactional
    @Query("UPDATE Usuario u SET u.login = :login, u.password = :password WHERE u.id = :id")
    public void updateUsuarioById(Integer id, String login, String password);

}
