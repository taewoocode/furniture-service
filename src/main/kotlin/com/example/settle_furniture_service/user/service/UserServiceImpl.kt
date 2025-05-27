package com.example.settle_furniture_service.user.service

import com.example.settle_furniture_service.security.JwtProvider
import com.example.settle_furniture_service.user.domain.User
import com.example.settle_furniture_service.user.dto.FindUserByEmailInfo
import com.example.settle_furniture_service.user.dto.FindUserByIdInfo
import com.example.settle_furniture_service.user.dto.SignUpInfo
import com.example.settle_furniture_service.user.dto.UserLoginInfo
import com.example.settle_furniture_service.user.repository.UserRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional

@Service
class UserServiceImpl(
    private val userRepository: UserRepository,
    private val passwordEncoder: PasswordEncoder,
    private val jwtProvider: JwtProvider
) : UserService {


    /**
     * 회원가입 처리
     * @param request 회원가입 요청 정보
     * @return 회원가입 결과
     */
    @Transactional
    override fun signUp(request: SignUpInfo.SignUpInfoRequest): SignUpInfo.SignUpInfoResponse {
        // 이메일 중복 체크
        if (userRepository.existsByEmail(request.email)) {
            throw IllegalArgumentException("이미 존재하는 이메일입니다")
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
            .profileImageUrl(request.profileImageUrl)
            .build()

        // 사용자 저장
        val savedUser = userRepository.save(user)

        // JWT 토큰 생성
        val token = jwtProvider.generateToken(savedUser)

        return SignUpInfo.SignUpInfoResponse(
            id = savedUser.id!!,
            email = savedUser.email,
            name = savedUser.name,
            role = savedUser.role,
            token = token
        )
    }

    /**
     * 로그인 처리
     * @param request 로그인 요청 정보 (이메일, 비밀번호)
     * @return 로그인 결과 (사용자 정보와 JWT 토큰)
     * @throws IllegalArgumentException 이메일이 존재하지 않거나 비밀번호가 일치하지 않는 경우
     */
    @Transactional(readOnly = true)
    override fun login(request: UserLoginInfo.UserLoginInfoRequest): UserLoginInfo.UserLoginInfoResponse {
        // 사용자 조회
        val user = userRepository.findByEmail(request.email)
            ?: throw IllegalArgumentException("존재하지 않는 이메일입니다")

        // 비밀번호 검증
        if (!passwordEncoder.matches(request.password, user.password)) {
            throw IllegalArgumentException("비밀번호가 일치하지 않습니다")
        }

        // JWT 토큰 생성
        val token = jwtProvider.generateToken(user)

        return UserLoginInfo.UserLoginInfoResponse(
            id = user.id!!,
            email = user.email,
            name = user.name,
            role = user.role,
            token = token
        )
    }

    /**
     * 이메일로 사용자 조회
     * @param request 이메일로 사용자 조회 요청 정보
     * @return 사용자 정보
     */
    @Transactional(readOnly = true)
    override fun findByEmail(request: FindUserByEmailInfo.FindUserByEmailRequest): FindUserByEmailInfo.FindUserByEmailResponse {
        val user = userRepository.findByEmail(request.email)
            ?: throw IllegalArgumentException("존재하지 않는 이메일입니다")

        return FindUserByEmailInfo.FindUserByEmailResponse(
            id = user.id!!,
            email = user.email,
            name = user.name,
            phoneNumber = user.phoneNumber,
            address = user.address,
            profileImageUrl = user.profileImageUrl,
            role = user.role
        )
    }

    /**
     * ID로 사용자 조회
     * @param request 조회할 사용자의 ID
     * @return 사용자 정보
     */
    @Transactional(readOnly = true)
    override fun findById(request: FindUserByIdInfo.FindUserByIdRequest): FindUserByIdInfo.FindUserByIdResponse {
        val user = userRepository.findById(request.id)
            .orElseThrow { IllegalArgumentException("존재하지 않는 사용자입니다") }

        return FindUserByIdInfo.FindUserByIdResponse(
            id = user.id!!,
            email = user.email,
            name = user.name,
            phoneNumber = user.phoneNumber,
            address = user.address,
            profileImageUrl = user.profileImageUrl,
            role = user.role
        )
    }
} 