package com.example.settle_furniture_service.config

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.security.config.annotation.web.builders.HttpSecurity
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity
import org.springframework.security.config.http.SessionCreationPolicy
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.security.web.SecurityFilterChain

/**
 * Spring Security 설정 클래스
 */
@Configuration
@EnableWebSecurity
class SecurityConfig {

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
            .sessionManagement { it.sessionCreationPolicy(SessionCreationPolicy.STATELESS) }
            .authorizeHttpRequests { auth ->
                auth
                    .requestMatchers("/api/v1/auth/**").permitAll()
                    .requestMatchers("/api/v1/users/signup").permitAll()
                    .anyRequest().authenticated()
            }
            .build()
    }
} 