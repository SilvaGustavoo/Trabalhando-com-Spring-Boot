package api_security.api_security;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import javax.management.relation.Role;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import api_security.api_security.repository.RoleRepository;
import api_security.api_security.repository.UsersRepository;
import api_security.api_security.user.UserRole;
import api_security.api_security.user.Users;

@Component
public class MyApp implements CommandLineRunner {

    @Autowired
    UsersRepository usersRepository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    @Override
    @Transactional
    public void run(String... args) throws Exception {
        var roleAdmin = roleRepository.findByRole(UserRole.Role.ADMIN.name());
        Set<UserRole> roles = new HashSet<>();
        
        // Pega todas as Roles registradas e atribui a uma lista
        for (UserRole.Role role : UserRole.Role.values()) {
            roles.add(roleRepository.findByRole(role.name()));
            System.out.println("\n\n\n"+ role.name());

            
        }

        System.out.println(roles);

        var userAdmin = usersRepository.findByLogin("admin");


        // Visualiza se ja existe um Users - Admin se não existir cria um novo
        if(userAdmin.isPresent()) {
            System.out.println("Admin ja existe");
        } else {
            Users user = new Users(
                "admin", 
                passwordEncoder.encode("123456"), 
                roles);

            usersRepository.save(user);
        }
    }
    
}
