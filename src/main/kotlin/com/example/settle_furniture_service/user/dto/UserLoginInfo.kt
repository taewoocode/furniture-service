package com.example.settle_furniture_service.user.dto

import com.example.settle_furniture_service.user.enum.UserRole
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank

/**
 * 로그인 관련 DTO들을 관리하는 파일
 */
object UserLoginInfo {
    /**
     * 로그인 요청 DTO
     */
    @Schema(
        description = "로그인 요청",
        example = """
            {
                "email": "user@example.com",
                "password": "Password123!@#"
            }
        """
    )
    data class UserLoginInfoRequest(
        @Schema(
            description = "사용자 이메일",
            example = "user@example.com",
            required = true
        )
        @field:NotBlank(message = "이메일은 필수 입력값입니다")
        @field:Email(message = "올바른 이메일 형식이 아닙니다")
        val email: String,

        @Schema(
            description = "사용자 비밀번호",
            example = "Password123!@#",
            required = true
        )
        @field:NotBlank(message = "비밀번호는 필수 입력값입니다")
        val password: String
    )

    /**
     * 로그인 응답 DTO
     */
    @Schema(
        description = "로그인 응답",
        example = """
            {
                "id": 1,
                "email": "user@example.com",
                "name": "홍길동",
                "role": "USER",
                "token": "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9..."
            }
        """
    )
    data class UserLoginInfoResponse(
        @Schema(description = "사용자 ID", example = "1")
        val id: Long,

        @Schema(description = "사용자 이메일", example = "user@example.com")
        val email: String,

        @Schema(description = "사용자 이름", example = "홍길동")
        val name: String,

        @Schema(description = "사용자 권한", example = "USER")
        val role: UserRole,

        @Schema(description = "JWT 토큰", example = "eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9...")
        val token: String
    )
} 