package SilvaGustavoo.primeiros_passos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import jakarta.annotation.Generated;




public class Remetente {

    private String email;
    private String nome;

    public Remetente(String email, String nome) {
        this.email = email;
        this.nome = nome;
    }

    public Remetente() {
        
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    @Override
    public String toString() {
        return "Remetente [email=" + email + ", nome=" + nome + "]";
    }
    

    
}
