package com.example.settle_furniture_service.config

import com.example.settle_furniture_service.security.JwtAuthenticationFilter
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter
import org.springframework.web.cors.CorsConfiguration
import org.springframework.web.cors.CorsConfigurationSource
import org.springframework.web.cors.UrlBasedCorsConfigurationSource

/**
 * Spring Security 설정 클래스
 */
@Configuration
@EnableWebSecurity
class SecurityConfig(
    private val jwtAuthenticationFilter: JwtAuthenticationFilter
) {

    /**
     * PasswordEncoder 빈을 생성합니다.
     * @return BCryptPasswordEncoder 인스턴스
     */
    @Bean
    fun passwordEncoder(): PasswordEncoder {
        return BCryptPasswordEncoder()
    }

    /**
     * SecurityFilterChain 빈을 생성합니다.
     * @param http HttpSecurity 인스턴스
     * @return SecurityFilterChain 인스턴스
     */
    @Bean
    fun securityFilterChain(http: HttpSecurity): SecurityFilterChain {
        return http
            .csrf { it.disable() }
            .cors { it.configurationSource(corsConfigurationSource()) }
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { auth ->
                auth
                    // Swagger UI 관련 경로 허용
                    .requestMatchers("/swagger-ui/**", "/swagger-ui.html", "/api-docs/**").permitAll()
                    // 인증 관련 경로 허용
                    .requestMatchers("/api/v1/auth/**").permitAll()
                    .requestMatchers("/api/v1/users/signup", "/api/v1/users/signup/**").permitAll()
                    // 나머지 요청은 인증 필요
                    .anyRequest().authenticated()
            }
            .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter::class.java)
            .build()
    }

    /**
     * CORS 설정을 위한 빈을 생성합니다.
     * @return CorsConfigurationSource 인스턴스
     */
    @Bean
    fun corsConfigurationSource(): CorsConfigurationSource {
        val configuration = CorsConfiguration()
        configuration.allowedOrigins = listOf("*")
        configuration.allowedMethods = listOf("GET", "POST", "PUT", "DELETE", "OPTIONS", "PATCH")
        configuration.allowedHeaders = listOf("Authorization", "Content-Type", "X-Requested-With", "Accept", "Origin", "Access-Control-Request-Method", "Access-Control-Request-Headers")
        configuration.exposedHeaders = listOf("Authorization")
        configuration.allowCredentials = true
        configuration.maxAge = 3600L

        val source = UrlBasedCorsConfigurationSource()
        source.registerCorsConfiguration("/**", configuration)
        return source
    }
} 