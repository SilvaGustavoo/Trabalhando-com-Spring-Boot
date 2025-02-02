package configuration.properties.usando_configuration_properties;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

/**
 * A Propriedade Value é utilizada para coletar conteudo do arquivo application.properties e automaticamente inser-los nos campos refereciados a abaixo da chamada.
 */
@Component
public class SistemaMensagens implements CommandLineRunner {

    // Caso não enconte o valor 'nome' ele usa o valor 'No Found' como padrão
    @Autowired
    private Remetente remetente;

    
    @Override
    public void run(String... args) throws Exception {

        System.out.println("ENVIANDO MENSAGENS");
        System.out.println("Nome: " + remetente.getNome());
        System.out.println("Email: " + remetente.getEmail());
        System.out.println("Telefones: " + remetente.getTelefones());
        System.out.println("\nSeu cadastro foi aprovado!!");
    }
    
}
