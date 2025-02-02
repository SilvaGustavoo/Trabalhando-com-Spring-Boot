package SilvaGustavoo.primeiros_passos;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PrimeirosPassosApplication {

	public static void main(String[] args) {
		SpringApplication.run(PrimeirosPassosApplication.class, args);
	}

	@Bean
    public CommandLineRunner run(SistemaDeMensagem mensagens) {
       
       return args -> {
            mensagens.enviarConfirmacaoDeCadastro();
            mensagens.enviarMensagemDeBoasVindas();
			mensagens.enviarConfirmacaoDeCadastro();
       };

    }

}
 