package com.example.settle_furniture_service.user.dto

import com.example.settle_furniture_service.user.enum.UserRole
import io.swagger.v3.oas.annotations.media.Schema

/**
 * ID로 사용자 조회 관련 DTO들을 관리하는 파일
 */
object FindUserByIdInfo {
    /**
     * ID로 사용자 조회 요청 DTO
     */
    @Schema(
        description = "ID로 사용자 조회 요청",
        example = """
            {
                "id": 1
            }
        """
    )
    data class FindUserByIdRequest(
        @Schema(
            description = "사용자 ID",
            example = "1",
            required = true
        )
        val id: Long
    )

    /**
     * ID로 사용자 조회 응답 DTO
     */
    @Schema(
        description = "ID로 사용자 조회 응답",
        example = """
            {
                "id": 1,
                "email": "user@example.com",
                "name": "홍길동",
                "phoneNumber": "010-1234-5678",
                "address": "서울시 강남구",
                "profileImageUrl": "https://example.com/profile.jpg",
                "role": "USER"
            }
        """
    )
    data class FindUserByIdResponse(
        @Schema(description = "사용자 ID", example = "1")
        val id: Long,

        @Schema(description = "사용자 이메일", example = "user@example.com")
        val email: String,

        @Schema(description = "사용자 이름", example = "홍길동")
        val name: String,

        @Schema(description = "사용자 전화번호", example = "010-1234-5678")
        val phoneNumber: String,

        @Schema(description = "사용자 주소", example = "서울시 강남구")
        val address: String,

        @Schema(description = "사용자 프로필 이미지 URL", example = "https://example.com/profile.jpg")
        val profileImageUrl: String?,

        @Schema(description = "사용자 권한", example = "USER")
        val role: UserRole
    )
}