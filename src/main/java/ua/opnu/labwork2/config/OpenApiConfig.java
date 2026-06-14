package ua.opnu.labwork2.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("Backend-сервіс управління спортивним клубом")
                        .version("1.0.0")
                        .description("REST API для автоматизації обліку тренерів, клієнтів, " +
                                "тренувань та записів на заняття у фітнес-центрі. " +
                                "Реалізовано на базі Spring Boot 3, Spring Data JPA та Oracle DB.")
                        .contact(new Contact()
                                .name("Кафедра ІКС, ОНПУ")
                                .email("student.opnu@gmail.com")));
    }
}
