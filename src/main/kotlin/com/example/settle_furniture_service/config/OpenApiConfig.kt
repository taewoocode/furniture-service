package com.example.settle_furniture_service.config

import io.swagger.v3.oas.models.Components
import io.swagger.v3.oas.models.OpenAPI
import io.swagger.v3.oas.models.info.Info
import io.swagger.v3.oas.models.security.SecurityRequirement
import io.swagger.v3.oas.models.security.SecurityScheme
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

/**
 * OpenAPI(Swagger) 설정 클래스
 */
@Configuration
class OpenApiConfig {

    @Bean
    fun openAPI(): OpenAPI {
        val info = Info()
            .title("Settle Furniture Service API")
            .version("v1.0.0")
            .description("가구 정리 서비스 API 문서")

        val securityScheme = SecurityScheme()
            .type(SecurityScheme.Type.HTTP)
            .scheme("bearer")
            .bearerFormat("JWT")
            .`in`(SecurityScheme.In.HEADER)

        val securityRequirement = SecurityRequirement()
            .addList("bearerAuth")

        return OpenAPI()
            .info(info)
            .components(Components().addSecuritySchemes("bearerAuth", securityScheme))
            .addSecurityItem(securityRequirement)
    }
} 