package configuration.properties.usando_configuration_properties;

import java.util.List;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

/**
 * A Configuration nessa situação está utilizado para que colete os daods da application.properties de maneira automatica e com o uso de um determinado prefixo, atecedendo a variavel 'remetente.nome=...'
 * E assim geralmente usado para objetos Beans que podem ter inicialização com dados padrões configurada ou não
 */
@Configuration
@ConfigurationProperties(prefix = "remetente")
public class Remetente {

    private String nome;
    private String email;
    private List<Long> telefones;

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public List<Long> getTelefones() {
        return telefones;
    }
    public void setTelefones(List<Long> telefones) {
        this.telefones = telefones;
    }
    
}
