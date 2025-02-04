package spring.api.spring_api.handler;


import java.net.http.HttpResponse;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

/**
 * Para definir onde se encontrará o tratamento de determinado na API Response, é necessário inserir o @RestControllerAdvice na classe desejada. 
 * Após isso defina a classe a qual você quer retornar uma mensagem. A maneira que você desenvolver a classe será o retorno do erro em JSON assim como um toString(), no meu caso será 
 * 
 * {
 * timestamp : {DATA E HORA ATUAL};
   status : "error";
   statusCode : {CÓDIGO ESPECIFICO};
   error : {MENSAGEM DE ERRO DEFINIDA};
 * }
 * 
 * Após definido crie a sua função de erro na classe de Exceptions @RestControllerAdvice nela deve conter como retorno a sua classe de Resposta de erro (no meu caso ResponseError) e definir suas variaveis.
 * 
 * Para que seja demonstrado o numero do erro HTTP, basta utilizar o @ResponseStatus e definir o HttpStatus. de acordo com o problema.
 * Para que o retorno do ResponseError seja em JSON é necessário atribuir o @ResponseBody 
 * E por fim é necessário informar a classe do erro quando executado, que no nosso caso foi o BusinessException
 * 
 * quando chamado o erro retorna
 * 
 * {
    "statusCode": 428,
    "error": "O campo nome ou senha são obrigatórios",
    "timestamp": "2025-02-04T22:38:52.831+00:00",
    "status": "error"
}
 */

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ResponseStatus(HttpStatus.PRECONDITION_REQUIRED)
    @ResponseBody
    @ExceptionHandler(BusinessException.class)
    public ResponseError handler(BusinessException ex) {

        // Cria uma mensagem de erro personalizada mostrando os valores da class ResponseError e personalizando o código do erro e a mensagem
        return new ResponseError(HttpStatus.PRECONDITION_REQUIRED.value(), ex.getMessage());
    }
    
}
