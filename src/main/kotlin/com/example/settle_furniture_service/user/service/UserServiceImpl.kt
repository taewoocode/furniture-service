package com.example.settle_furniture_service.user.service

import com.example.settle_furniture_service.user.domain.User
import com.example.settle_furniture_service.user.dto.SignUpInfo
import com.example.settle_furniture_service.user.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

/**
 * UserService 인터페이스의 구현체
 */
@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder
) : UserService {
    /**
     * 회원가입 처리
     * @param request 회원가입 요청 정보
     * @return 회원가입 결과
     */
    @Transactional
    override fun signUp(request: SignUpInfo.Request): SignUpInfo.Response {
        // 이메일 중복 체크
        if (userRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException("이미 존재하는 이메일입니다.")
        }

        // 비밀번호 암호화
        val encodedPassword = passwordEncoder.encode(request.password)

        // 사용자 생성
        val user = User.builder()
            .email(request.email)
            .password(encodedPassword)
            .name(request.name)
            .phoneNumber(request.phoneNumber)
            .address(request.address)
            .profileImageUrl(request.profileImage)
            .build()

        // 사용자 저장
        val savedUser = userRepository.save(user)

        // 응답 생성
        return SignUpInfo.Response(
            userId = savedUser.id!!,
            email = savedUser.email,
            name = savedUser.name,
            role = savedUser.role,
            token = "JWT_TOKEN" // TODO: JWT 토큰 생성 로직 구현
        )
    }

    /**
     * 이메일로 사용자 조회
     * @param email 조회할 사용자의 이메일
     * @return 사용자 정보
     */
    @Transactional(readOnly = true)
    override fun findByEmail(email: String): SignUpInfo.Response {
        val user = userRepository.findByEmail(email)
            ?: throw IllegalArgumentException("존재하지 않는 사용자입니다.")

        return SignUpInfo.Response(
            userId = user.id!!,
            email = user.email,
            name = user.name,
            role = user.role,
            token = "JWT_TOKEN" // TODO: JWT 토큰 생성 로직 구현
        )
    }

    /**
     * ID로 사용자 조회
     * @param id 조회할 사용자의 ID
     * @return 사용자 정보
     */
    @Transactional(readOnly = true)
    override fun findById(id: Long): SignUpInfo.Response {
        val user = userRepository.findById(id)
            .orElseThrow { IllegalArgumentException("존재하지 않는 사용자입니다.") }

        return SignUpInfo.Response(
            userId = user.id!!,
            email = user.email,
            name = user.name,
            role = user.role,
            token = "JWT_TOKEN" // TODO: JWT 토큰 생성 로직 구현
        )
    }
} 