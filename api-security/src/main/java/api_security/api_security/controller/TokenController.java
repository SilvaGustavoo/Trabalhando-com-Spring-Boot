package api_security.api_security.controller;

import org.springframework.web.bind.annotation.RestController;

import com.nimbusds.jwt.JWTClaimsSet;

import api_security.api_security.repository.UsersRepository;
import api_security.api_security.user.Users;
import api_security.api_security.user.dto.AuthenticatorDto;
import api_security.api_security.user.dto.LoginResponse;

import java.net.Authenticator;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtClaimsSet;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;


@RestController
public class TokenController {

    @Autowired
    private JwtEncoder encoder;
    @Autowired
    private UsersRepository usersRepository;
    @Autowired
    private BCryptPasswordEncoder passwordEncoder;
    
    @PostMapping("login")
    public ResponseEntity<LoginResponse> login(@RequestBody AuthenticatorDto userDto){
        
        Optional<Users> user = usersRepository.findByLogin(userDto.login());

        if(user.isEmpty() || user.get().validarLogin(userDto, passwordEncoder))  {
            throw new BadCredentialsException("Usuario ou senha inválidos");
        }


        // var clains = JwtClaimsSet.builder()

        
        return null;
    }
    
}
