package api_security.api_security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import api_security.api_security.user.Users;


public interface UsersRepository extends JpaRepository<Users, Integer> {

    public Optional<Users> findByLogin(String login);
}
