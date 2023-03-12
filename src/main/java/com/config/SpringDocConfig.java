package com.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springdoc.core.GroupedOpenApi;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @Author：Charles
 * @Package：com.config
 * @Project：EpidemicPreventionAndControl
 * @name：SpringDocConfig
 * @Date：3/12/2023 10:38 PM
 * @Filename：SpringDocConfig
 */
@Configuration
public class SpringDocConfig {
    @Bean
    public OpenAPI openAPI() {
        return new OpenAPI()
                .info(new Info().title("System API")
                        .description("SpringDoc API 演示")
                        .version("v1.0.0"))
                .externalDocs(new ExternalDocumentation()
                        .description("SpringDoc"));
    }

    @Bean
    public GroupedOpenApi publicApi() {
        return GroupedOpenApi.builder()
                .group("user")
                .pathsToMatch("/user/**")
                .build();
    }

    @Bean
    public GroupedOpenApi adminApi() {
        return GroupedOpenApi.builder()
                .group("admin")
                .pathsToMatch("/user/**")
                .build();
    }
}