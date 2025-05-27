package com.example.settle_furniture_service.common.docs

/**
 * Swagger 문서에 사용되는 상수들을 관리하는 클래스
 */
object SwaggerDocs {
    /**===================================================================
     *                      User 관련 Swagger Docs
     * ===================================================================
     */
    object User {
        // 회원가입
        const val SUMMARY_SIGNUP = "회원가입"
        const val DESCRIPTION_SIGNUP = "이메일, 비밀번호, 이름, 전화번호, 주소, 프로필 이미지를 입력하여 회원가입을 진행합니다."

        // 이메일로 사용자 조회
        const val SUMMARY_FIND_BY_EMAIL = "이메일로 사용자 조회"
        const val DESCRIPTION_FIND_BY_EMAIL = "이메일을 통해 사용자 정보를 조회합니다."

        // ID로 사용자 조회
        const val SUMMARY_FIND_BY_ID = "ID로 사용자 조회"
        const val DESCRIPTION_FIND_BY_ID = "ID를 통해 사용자 정보를 조회합니다."

        // 파라미터 설명
        const val PARAM_EMAIL = "사용자 이메일"
        const val PARAM_ID = "사용자 ID"
    }
} 