package api_security.api_security.repository;

import java.util.Optional;
import java.util.Set;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.security.core.userdetails.UserDetails;

import api_security.api_security.user.UserRole;
import api_security.api_security.user.Users;


public interface UsersRepository extends JpaRepository<Users, Integer> {

    public Optional<Users> findByLogin(String login);

/*     @Modifying
    @Query("UPDATE User u SET u.roles = :role WHERE u.id = :id")
    void updateRoleById(@Param("id") Integer id, @Param("role") Set<UserRole> role); */
    

}
