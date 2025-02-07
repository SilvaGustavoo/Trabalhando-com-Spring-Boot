package api_security.api_security.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import api_security.api_security.repository.UsersRepository;
import api_security.api_security.user.UserRole;
import api_security.api_security.user.Users;
import api_security.api_security.user.dto.AuthenticatorDto;
import api_security.api_security.user.dto.RegisterDto;
import jakarta.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    UsersRepository repository;
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    BCryptPasswordEncoder passwordEncoder;
    
    @PostMapping("/login")
    public ResponseEntity postLogin(@RequestBody @Valid AuthenticatorDto user) {

/*         System.out.println("User role: " + user.login());
        System.out.println("Authorities: " + user.password()); */

        var loginPassword = new UsernamePasswordAuthenticationToken(user.login(), user.password());

/*         System.out.println("User role: " + loginPassword.getName());
        System.out.println("Authorities: " + loginPassword.getAuthorities()); */

        
        var auth = authenticationManager.authenticate(loginPassword);
        
        
        System.out.println("\n\nUser pass: " + auth.getDetails());
        System.out.println("Authorities: " + auth.getAuthorities());

        return ResponseEntity.ok(auth.getAuthorities());
    }


    @PostMapping("/register")
    public ResponseEntity register(@RequestBody @Valid RegisterDto user) {
        
        if(repository.findByLogin(user.login()) != null) {
            return ResponseEntity.badRequest().build();
        }

        var userPassword = passwordEncoder.encode(user.password());

        repository.save(new Users(user.login(), userPassword, UserRole.USER));

        return ResponseEntity.ok().build();
    }
    
    
}
