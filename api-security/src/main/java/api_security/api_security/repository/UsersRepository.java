package api_security.api_security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.security.core.userdetails.UserDetails;

import api_security.api_security.user.Users;


public interface UsersRepository extends JpaRepository<Users, Integer> {

    public UserDetails findByLogin(String login);
}
