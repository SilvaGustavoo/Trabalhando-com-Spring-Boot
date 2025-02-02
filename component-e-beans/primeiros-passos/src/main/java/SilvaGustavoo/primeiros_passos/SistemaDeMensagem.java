package SilvaGustavoo.primeiros_passos;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class SistemaDeMensagem {

    @Autowired
    Remetente comercial;

    @Autowired
    Remetente RH;

    public void enviarConfirmacaoDeCadastro () {
        System.out.println("Para o remetente");
        System.out.println(comercial);
        System.out.println("Cadastro Realizado com Sucesso");
    }

    public void enviarMensagemDeBoasVindas() {
        System.out.println("Para o remetente");
        RH.setNome("Mensagem");
        RH.setEmail("rh@dio.com.br");
        System.out.println(RH);
        System.out.println("Mensagem de Boas Vindas");
    }
}
