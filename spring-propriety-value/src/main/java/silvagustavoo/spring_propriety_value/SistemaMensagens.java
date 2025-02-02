package silvagustavoo.spring_propriety_value;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * A Propriedade Value é utilizada para coletar conteudo do arquivo application.properties e automaticamente inser-los nos campos refereciados a abaixo da chamada.
 */
@Component
public class SistemaMensagens implements CommandLineRunner {

    // Caso não enconte o valor 'nome' ele usa o valor 'No Found' como padrão
    @Value("${nome:No Found}")
    private String nome;
    @Value("${email}")
    private String email;
    @Value("${telefones}")
    private List<Long> telefones;

    
    @Override
    public void run(String... args) throws Exception {

        System.out.println("ENVIANDO MENSAGENS");
        System.out.println("Nome: " + nome);
        System.out.println("Email: " + email);
        System.out.println("Telefones: " + telefones);
        System.out.println("\nSeu cadastro foi aprovado!!");
    }

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

    public void adicionarTelefone(Long telefone) {
        this.telefones.add(telefone);
    }

    public void setTelefones(List<Long> telefones) {
        this.telefones = telefones;
    }

    @Override
    public String toString() {
        return "SistemaMensagens [nome=" + nome + ", email=" + email + ", telefones=" + telefones + "]";
    }

    
    
}
