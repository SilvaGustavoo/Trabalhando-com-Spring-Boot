package api_security.api_security.repository;


import javax.management.relation.Role;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import api_security.api_security.user.UserRole;



@Repository
public interface RoleRepository extends JpaRepository<UserRole, Long> {

    UserRole findByRole(String string);
    
}
