package com.example.settle_furniture_service.user.service

import com.example.settle_furniture_service.user.dto.FindUserByEmailInfo
import com.example.settle_furniture_service.user.dto.FindUserByIdInfo
import com.example.settle_furniture_service.user.dto.SignUpInfo
import com.example.settle_furniture_service.user.dto.UserLoginInfo

/**
 * 사용자 관련 비즈니스 로직을 처리하는 서비스 인터페이스
 */
interface UserService {
    /**
     * 새로운 사용자를 등록합니다.
     * @param request 회원가입 요청 정보
     * @return 회원가입 응답 정보 (JWT 토큰 포함)
     * @throws IllegalArgumentException 이메일이 이미 존재하는 경우
     */
    fun signUp(request: SignUpInfo.SignUpInfoRequest)
            : SignUpInfo.SignUpInfoResponse

    /**
     * 로그인
     * @param request 로그인 요청 정보
     * @return 로그인 응답 정보
     */
    fun login(request: UserLoginInfo.UserLoginInfoRequest): UserLoginInfo.UserLoginInfoResponse

    /**
     * 이메일로 사용자를 조회합니다.
     * @param request 이메일로 사용자 조회 요청 정보
     * @return 사용자 정보 (JWT 토큰 포함)
     * @throws IllegalArgumentException 사용자가 존재하지 않는 경우
     */
    fun findByEmail(request: FindUserByEmailInfo.FindUserByEmailRequest)
            : FindUserByEmailInfo.FindUserByEmailResponse

    /**
     * ID로 사용자 조회 API
     * @param id 조회할 사용자의 ID
     * @return 사용자 정보
     */
    fun findById(request: FindUserByIdInfo.FindUserByIdRequest): FindUserByIdInfo.FindUserByIdResponse
} 