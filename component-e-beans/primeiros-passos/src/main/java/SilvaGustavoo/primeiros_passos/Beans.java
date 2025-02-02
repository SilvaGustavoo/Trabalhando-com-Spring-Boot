package SilvaGustavoo.primeiros_passos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


/**
 * Beans tem basicamente a mesma utilidade que o Componenet é utilizado para evitar o uso do inicializador dos objetos (new Object()), só que o beans é oara referenciar classes do Sistema, aqueles que não podem ser alterados por nós. Ex (Gson(), Date(), etc)
 * 
 * Bean também pode ser utilizado como iniciador padrão, para classes e argumentos que ao inicializar ja possuiram determinados valores.
 * 
 * Caso não seja realizado o uso do Scope("prototype"), o Beans utilizará os dados não só como padrão, mas também aplicará o mesmo objeto a diversos objetos, ou seja, se existirem dois objetos criado com nomes diferentes, qualquer alteração realizada, será aplicada a amobos.
 * 
 * EX: Pessoa rodolfo; (nome="padrão")
 * Pessoa ronaldo; (nome="padrao")
 * 
 * ronaldo.setNome("ronaldo")
 * 
 * LOGO -> rodolfo e ronaldo (nome="ronaldo"), para o codigo eles são iguais !!
 */
@Configuration
public class Beans {

    @Bean
    @Scope("prototype")
    public Remetente remetente() {
        System.out.println("CRIANDO OBJETO REMETENTE");

        Remetente remetente = new Remetente("comercial@Dio.com.br", "Comercial da DIO");

        return remetente;
    }


}
