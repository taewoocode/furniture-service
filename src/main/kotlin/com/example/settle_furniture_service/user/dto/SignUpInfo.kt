package com.example.settle_furniture_service.user.dto

import com.example.settle_furniture_service.user.enum.UserRole
import io.swagger.v3.oas.annotations.media.Schema
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern
import jakarta.validation.constraints.Size

/**
 * 회원가입 관련 DTO들을 관리하는 파일
 */
object SignUpInfo {
    /**
     * 회원가입 요청 DTO
     */
    @Schema(
        description = "회원가입 요청",
        example = """
            {
                "email": "user@example.com",
                "password": "Password123!@#",
                "name": "홍길동",
                "phoneNumber": "010-1234-5678",
                "address": "서울시 강남구",
                "profileImageUrl": "https://example.com/profile.jpg"
            }
        """
    )
    data class SignUpInfoRequest(
        @Schema(
            description = "사용자 이메일",
            example = "user@example.com",
            required = true
        )
        @field:NotBlank(message = "이메일은 필수 입력값입니다")
        @field:Email(message = "올바른 이메일 형식이 아닙니다")
        val email: String,

        @Schema(
            description = "사용자 비밀번호 (영문, 숫자, 특수문자 포함 8-20자)",
            example = "Password123!@#",
            required = true
        )
        @field:NotBlank(message = "비밀번호는 필수 입력값입니다")
        @field:Size(min = 8, max = 20, message = "비밀번호는 8-20자 사이여야 합니다")
        @field:Pattern(
            regexp = "^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,20}$",
            message = "비밀번호는 영문, 숫자, 특수문자를 포함해야 합니다"
        )
        val password: String,

        @Schema(
            description = "사용자 이름",
            example = "홍길동",
            required = true
        )
        @field:NotBlank(message = "이름은 필수 입력값입니다")
        @field:Size(min = 2, max = 20, message = "이름은 2-20자 사이여야 합니다")
        val name: String,

        @Schema(
            description = "사용자 전화번호",
            example = "010-1234-5678",
            required = true
        )
        @field:NotBlank(message = "전화번호는 필수 입력값입니다")
        @field:Pattern(
            regexp = "^01(?:0|1|[6-9])-(?:\\d{3}|\\d{4})-\\d{4}$",
            message = "올바른 전화번호 형식이 아닙니다"
        )
        val phoneNumber: String,

        @Schema(
            description = "사용자 주소",
            example = "서울시 강남구",
            required = true
        )
        @field:NotBlank(message = "주소는 필수 입력값입니다")
        val address: String,

        @Schema(
            description = "사용자 프로필 이미지 URL",
            example = "https://example.com/profile.jpg",
            required = false
        )
        val profileImageUrl: String? = null
    )
    

    /**
     * 회원가입 응답 DTO
     */
    @Schema(
        description = "회원가입 응답",
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
    data class SignUpInfoResponse(
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