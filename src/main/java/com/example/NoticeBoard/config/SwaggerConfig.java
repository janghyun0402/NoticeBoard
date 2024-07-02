package com.example.NoticeBoard.config;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Arrays;
import java.util.Collections;

@Configuration
public class SwaggerConfig {

    @Bean
    public OpenAPI openAPI() {
        OpenAPI openAPI = new OpenAPI()
                .info(new Info()
                        .title("My API")
                        .description("API 설명")
                        .version("1.0"));
        openAPI.addExtension("x-include-patterns", Collections.singletonList("/.*"));
        return openAPI;
    }
}
