package api_security.api_security.security;

import java.security.interfaces.RSAPrivateKey;
import java.security.interfaces.RSAPublicKey;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authorization.AuthorityAuthorizationManager;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.authentication.configuration.AuthenticationConfiguration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityCustomizer;
import org.springframework.security.config.annotation.web.configurers.AuthorizeHttpRequestsConfigurer.AuthorizationManagerRequestMatcherRegistry;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.oauth2.jwt.JwtDecoder;
import org.springframework.security.oauth2.jwt.JwtEncoder;
import org.springframework.security.oauth2.jwt.NimbusJwtDecoder;
import org.springframework.security.oauth2.jwt.NimbusJwtEncoder;
import org.springframework.security.web.SecurityFilterChain;

import com.nimbusds.jose.jwk.JWK;
import com.nimbusds.jose.jwk.JWKSet;
import com.nimbusds.jose.jwk.RSAKey;
import com.nimbusds.jose.jwk.source.ImmutableJWKSet;

import api_security.api_security.user.UserRole;

@Configuration
@EnableWebSecurity
@EnableMethodSecurity
public class SecurityConfiguration {

    @Value("${jwt.private.key}")
    private RSAPrivateKey priKey;
    @Value("${jwt.public.key}")
    private RSAPublicKey pubKey;

    /**
     * Csrf seria uma configuração padrão do HttpSecurity, como vamos personalizar, nos desativamos
     * 
     * Agora utilizamos o 
     * @param http
     * @return
          * @throws Exception 
          */
         @Bean
    public SecurityFilterChain filtrarUsuario(HttpSecurity http) throws Exception {

        return http
            .csrf(csrf -> csrf.disable())
            .authorizeHttpRequests(authorize -> authorize
            .requestMatchers(HttpMethod.POST, "/auth/login").permitAll()
            .requestMatchers(HttpMethod.POST, "/system/register").permitAll()
            .requestMatchers(HttpMethod.GET, "/produtos").permitAll()
            .anyRequest().authenticated())
            // Criado a configuração JWT para usa-la no projeto
            .oauth2ResourceServer(auth -> auth.jwt(Customizer.withDefaults()))
            // Demonstra o tipo de salvamento dos dados 
            .sessionManagement(session -> session.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
            .build();
    }

    
    @Bean
    public JwtDecoder jwtEncoder() {
        return NimbusJwtDecoder.withPublicKey(pubKey).build();
    }

    // Função utilizada para Encriptar a senha e transforma-la em HashCode para ser salva no banco de dados
    @Bean
    public JwtEncoder passwordEncoder() {
        // Cria uma criptografia com base na chave publica criada
        JWK jwk = new RSAKey.Builder(pubKey).privateKey(priKey).build();
        var jwks = new ImmutableJWKSet<>(new JWKSet(jwk));

        return new NimbusJwtEncoder(jwks);
    }

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public WebSecurityCustomizer webSecurityCustomizer() {
        return (web) -> web.ignoring().requestMatchers("/swagger-ui/**", "/v3/api-docs/**");
    }

}
