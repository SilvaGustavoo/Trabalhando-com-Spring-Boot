package spring.boot.aula_spring_boot_data_jpa;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import spring.boot.aula_spring_boot_data_jpa.model.Aluno;
import spring.boot.aula_spring_boot_data_jpa.repository.AlunoRepository;


@Component
public class MyApp implements CommandLineRunner{
    @Autowired
    AlunoRepository repository;
    @Override
    public void run(String... args) throws Exception {
        
        Aluno aluno = new Aluno();

        aluno.setNome("SilvaGustavoo");
        aluno.setEmail("gustavosantos728@yahoo.com.br");
        aluno.setEndereco("São paulo - SP");
        aluno.setClasse("1");
        aluno.setIdade(23);

        System.out.println("\n\n ");


        for (Aluno a : repository.pegarIdade(22)) {
            System.out.println(a);
        }

        System.out.println("\n\n\n");
        
        // Mostra todos os alunos que estão em uma classe que tenha o 1 no nome - usando @Query()
        for (Aluno a : repository.contemNaClasse("1")) {
            System.out.println(a);
        }

        // Mesma coisa que acima, mas usando o QueryMethod
        for (Aluno a : repository.findByClasseContaining("1")) {
            System.out.println(a);
        }


        System.out.println("\n\n");


        // adiciona ao repository o aluno
        repository.save(aluno);


        System.out.println("\n\n ");

        // imprimi todos os alunos listados no repository
        for (Aluno a : repository.findAll()) {
            System.out.println(a);
        }

        System.out.println("\n\n ");

        
        
    }
    
}
