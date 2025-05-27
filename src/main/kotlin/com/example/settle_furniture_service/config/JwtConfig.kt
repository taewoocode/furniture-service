package com.example.settle_furniture_service.config

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

/**
 * JWT 관련 설정을 관리하는 클래스
 */
@Configuration
@ConfigurationProperties(prefix = "jwt")
class JwtConfig {
    /** JWT 시크릿 키 */
    lateinit var secret: String

    /** JWT 토큰 만료 시간 (밀리초) */
    var expirationTime: Long = 86400000 // 24시간

    /** JWT 토큰 발급자 */
    lateinit var issuer: String
} 