package com.estudios.virtuales.estudios.virtuales.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import org.springframework.context.annotation.Configuration;

//esta es la clase para configurar swagger
@Configuration
@OpenAPIDefinition(
        info=@Info(title="Api para administracion de cursos virtuales",
        version="1.0",
        description = "esta api maneja las relaciones de user,enrollment,courses,lesson.task,submissions y message"
        )
)
public class OpenApiConfig {
}
