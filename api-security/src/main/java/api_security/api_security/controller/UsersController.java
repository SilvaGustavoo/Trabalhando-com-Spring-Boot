package api_security.api_security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api_security.api_security.model.Carrinho;
import api_security.api_security.model.CarrinhoItem;
import api_security.api_security.repository.CarrinhoRepository;
import api_security.api_security.repository.RoleRepository;
import api_security.api_security.repository.UsersRepository;
import api_security.api_security.service.ServiceUser;
import api_security.api_security.user.UserRole;
import api_security.api_security.user.Users;
import api_security.api_security.user.dto.AuthenticatorDto;
import api_security.api_security.user.dto.RegisterDto;
import jakarta.validation.Valid;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/system")
public class UsersController {

    @Autowired
    private UsersRepository repository;
    @Autowired
    private ServiceUser service;

    @GetMapping("/")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<Users> getUsers() {
        return repository.findAll();
    }

    @GetMapping("/{login}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public List<Users> getUsersByLogin(@PathVariable String login) {
        Optional<Users> user = repository.findByLogin(login);
        if (user.isEmpty()) {
            return List.of();
        }
        return List.of(user.get());
    }

    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDto user) {
        
        return service.register(user);
    }
    

    @PostMapping("/register/admin")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity registerAdmin(@RequestBody @Valid RegisterDto user) {
        
        return service.registerAdmin(user);

    }


    @DeleteMapping("/delete/{login}")
    @PreAuthorize("hasAuthority('SCOPE_ADMIN')")
    public ResponseEntity deleteUser(@PathVariable String login) {
        
        return service.deleteUser(login);
    }


}
