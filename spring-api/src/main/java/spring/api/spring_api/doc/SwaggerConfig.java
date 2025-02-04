package spring.api.spring_api.doc;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * http://localhost:8080/swagger-ui/index.html
 */
@Configuration
public class SwaggerConfig {

    public Contact contato() {
        return new Contact()
        .name("Gustavo Santos Silva")
        .url("https://github.com/silvagustavoo")
        .email("gustavosantos728@yahoo.com.br");
    }

    public Info informacoesApi() {
        return new Info()
                .title("API Spring Boot")
                .description("API para gerenciamento de usuários")
                .version("1.0.0")
                .contact(contato())
                .termsOfService("Termo de Uso: Open Source")
                .license(new io.swagger.v3.oas.models.info.License()
                .name("Aprendendo Spring Boot")
                .url("https://github.com/silvagustavoo"));
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(informacoesApi())
                .externalDocs(new ExternalDocumentation()
                        .description("Documentação Completa")
                        .url("https://github.com/silvagustavoo"));
    }
}
