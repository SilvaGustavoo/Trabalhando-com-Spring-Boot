package api_security.api_security.service;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import api_security.api_security.model.Carrinho;
import api_security.api_security.repository.CarrinhoRepository;
import api_security.api_security.repository.RoleRepository;
import api_security.api_security.repository.UsersRepository;
import api_security.api_security.user.UserRole;
import api_security.api_security.user.Users;
import api_security.api_security.user.dto.RegisterDto;
import jakarta.validation.Valid;

@Service
public class ServiceUser {
    
    @Autowired
    UsersRepository repository;
    @Autowired
    RoleRepository roleRepository;
    @Autowired
    CarrinhoRepository carrinhoRepository;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;

    public ResponseEntity register(RegisterDto user) {
        
        if(!repository.findByLogin(user.login()).isEmpty()) {
            return ResponseEntity.badRequest().body("User already registered");
        }

        
        var userPassword = passwordEncoder.encode(user.password());
        
        repository.save(new Users(user.login(), userPassword, Set.of(roleRepository.findByRole(UserRole.Role.USER.name()))));

        carrinhoRepository.save(new Carrinho(new HashSet<>(), repository.findByLogin(user.login()).get()));
        

        return ResponseEntity.ok().body("User registred sucessful");
    }


    public ResponseEntity registerAdmin(RegisterDto user) {
        Optional<Users> usersExistente = repository.findByLogin(user.login());
        Set<UserRole> roles = new HashSet<>();

        // Coleta todas as roles existentes
        for (UserRole.Role role: UserRole.Role.values()) {
            roles.add(roleRepository.findByRole(role.name()));
        }

        // Verifica se o usuario já existe e se não possui a role ADMIN
        if(usersExistente.isPresent() && !usersExistente.get().getRole().contains(roleRepository.findByRole(UserRole.Role.ADMIN.name()))) {

            usersExistente.get().setRole(roles);
            repository.save(usersExistente.get());
            
            return ResponseEntity.ok("Usuario " + usersExistente.get().getLogin() + " atualizado para admin");
        }

        if (usersExistente.get().getRole().contains(roleRepository.findByRole(UserRole.Role.ADMIN.name()))) {
            return ResponseEntity.ok("Ele ja é um admin");
        }

        usersExistente.get().setLogin(user.login());
        usersExistente.get().setPassword(passwordEncoder.encode(user.password()));
        usersExistente.get().setRole(roles);
        repository.save(usersExistente.get());

        return ResponseEntity.ok("Usuario " + usersExistente.get().getLogin() + " criado como admin");

    }


    public ResponseEntity deleteUser(String login) {
        Optional<Users> usersExistente = repository.findByLogin(login);

        if(usersExistente.isPresent()) {
            repository.delete(usersExistente.get());
            return ResponseEntity.ok("Usuario " + usersExistente.get().getLogin() + " deletado");
        }

        try {
            usersExistente = repository.findById(Integer.parseInt(login));
        } catch (Exception e) {
            usersExistente.empty();
        }
        
        if(usersExistente.isPresent()) {
            repository.delete(usersExistente.get());
            return ResponseEntity.ok("Usuario " + usersExistente.get().getLogin() + " deletado");
        }

        return ResponseEntity.badRequest().body("Usuario não encontrado");
    }


}
