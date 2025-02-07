package api_security.api_security.doc;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;

@Configuration
public class SwaggerConfig {

    public Contact contato() {
        return new Contact()
                    .email("gustavosantos728@gmail.com")
                    .name("SilvaGustavoo")
                    .url("https://github.com/SilvaGustavoo");
    }

    public Info informacoesApi() {
        return new Info()
                .contact(contato())
                .description("API de Autenticação e Autorização")
                .license(new io.swagger.v3.oas.models.info.License())
                .termsOfService("Termos de Uso: Open Source")
                .title("Spring API Security")
                .version("1.0.0");
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(informacoesApi())
                .externalDocs(new ExternalDocumentation()
                .description("Documentação Completa")
                .url("https://github.com/SilvaGustavoo"));
    }
}
