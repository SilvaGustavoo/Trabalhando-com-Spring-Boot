package spring.boot.aula_spring_boot_data_jpa.model;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;

/**
 * Para incializar uma classe que fará parte de um banco de dados, é necessário a incialização com o @Entity, para que seja reconhecido como tal
 * 
 * Outras atribuções importantes são:
 * - Definição de um @Id e sua Auto-geração com o @GeneratedValue e especificar a sua strategy
 * - Definir caracteristicas a cada coluna, caso seja necessário, como nullable, name, entre outras definidas pelo modelo de banco de dados criado
 * 
 */
@Entity
public class Aluno {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "ra_aluno")
    private Integer ra;

    @Column(name = "nome_aluno", nullable = false)
    private String nome;

    @Column(name = "idade_aluno", nullable = false)
    private int idade;

    @Column(name = "email_aluno", nullable = false)
    private String email;

    @Column(name = "endereco_aluno", nullable = false)
    private String endereco;

    @Column(name = "classe_aluno")
    private String classe;

    


    public Aluno(String nome, int idade, String email, String endereco, String classe) {
        this.nome = nome;
        this.idade = idade;
        this.email = email;
        this.endereco = endereco;
    }

    public Aluno() {
    }

    public Integer getRa() {
        return ra;
    }

    public String getNome() {
        return nome;
    }
    public void setNome(String nome) {
        this.nome = nome;
    }
    public int getIdade() {
        return idade;
    }
    public void setIdade(int idade) {
        this.idade = idade;
    }
    public String getEmail() {
        return email;
    }
    public void setEmail(String email) {
        this.email = email;
    }
    public String getEndereco() {
        return endereco;
    }
    public void setEndereco(String endereco) {
        this.endereco = endereco;
    }
    public String getClasse() {
        return classe;
    }
    public void setClasse(String classe) {
        this.classe = classe;
    }

    @Override
    public String toString() {
        return "\n Aluno [ra=" + ra + ", nome=" + nome + ", idade=" + idade + ", email=" + email + ", endereco=" + endereco
                + ", classe=" + classe + "]";
    }

    

    
}
