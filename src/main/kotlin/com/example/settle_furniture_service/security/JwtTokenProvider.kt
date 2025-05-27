package com.example.settle_furniture_service.security

import com.example.settle_furniture_service.config.JwtConfig
import com.example.settle_furniture_service.user.domain.User
import io.jsonwebtoken.Claims
import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import org.springframework.security.core.userdetails.UserDetails
import org.springframework.stereotype.Component
import java.util.*

/**
 * JWT 토큰을 생성하고 검증하는 서비스
 */
@Component
class JwtTokenProvider(
    private val jwtConfig: JwtConfig
) {
    /**
     * 사용자 정보로 JWT 토큰을 생성합니다.
     * @param user 토큰을 생성할 사용자
     * @return 생성된 JWT 토큰
     */
    fun generateToken(user: User): String {
        val now = Date()
        val expiration = Date(now.time + jwtConfig.expirationTime)

        return Jwts.builder()
            .setSubject(user.email)
            .claim("userId", user.id)
            .claim("role", user.role.name)
            .setIssuedAt(now)
            .setExpiration(expiration)
            .setIssuer(jwtConfig.issuer)
            .signWith(SignatureAlgorithm.HS256, jwtConfig.secret)
            .compact()
    }

    /**
     * JWT 토큰에서 사용자 이메일을 추출합니다.
     * @param token JWT 토큰
     * @return 사용자 이메일
     * @throws JwtException 토큰이 유효하지 않은 경우
     */
    fun getEmailFromToken(token: String): String {
        return Jwts.parser()
            .setSigningKey(jwtConfig.secret)
            .parseClaimsJws(token)
            .body
            .subject
    }

    /**
     * JWT 토큰이 유효한지 검증합니다.
     * @param token 검증할 JWT 토큰
     * @param userDetails 토큰의 주체가 되는 사용자 정보
     * @return 토큰이 유효하면 true, 아니면 false
     */
    fun validateToken(token: String, userDetails: UserDetails): Boolean {
        return try {
            val claims: Claims = Jwts.parser()
                .setSigningKey(jwtConfig.secret)
                .parseClaimsJws(token)
                .body

            val email = claims.subject
            val isTokenExpired = claims.expiration.before(Date())

            email == userDetails.username && !isTokenExpired
        } catch (e: Exception) {
            false
        }
    }
} 