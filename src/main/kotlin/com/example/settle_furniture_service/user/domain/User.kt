package com.example.settle_furniture_service.user.domain

import com.example.settle_furniture_service.user.enum.UserRole
import jakarta.persistence.*
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import org.springframework.data.jpa.domain.support.AuditingEntityListener
import org.springframework.security.crypto.password.PasswordEncoder
import java.time.LocalDateTime

/**
 * 사용자 정보를 저장하는 도메인 클래스
 * 사용자와 관련된 모든 비즈니스 로직을 캡슐화
 */
@Entity
@Table(name = "users")
@EntityListeners(AuditingEntityListener::class)
class User private constructor(
    /** 사용자의 고유 식별자 */
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long? = null,

    /** 사용자의 이메일 주소 (로그인 ID로 사용) */
    @Column(nullable = false, unique = true)
    val email: String,

    /** 사용자의 비밀번호 (암호화되어 저장) */
    @Column(nullable = false)
    var password: String,

    /** 사용자의 실명 */
    @Column(nullable = false)
    val name: String,

    /** 사용자의 전화번호 (010-XXXX-XXXX 형식) */
    @Column(nullable = false)
    val phoneNumber: String,

    /** 사용자의 주소 */
    @Column(nullable = false)
    var address: String,

    /** 사용자의 프로필 이미지 URL */
    @Column
    val profileImageUrl: String? = null,

    /** 사용자의 권한 (USER 또는 ADMIN) */
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    val role: UserRole = UserRole.USER,

    /** 계정 생성 시간 */
    @CreatedDate
    @Column(nullable = false, updatable = false)
    val createdAt: LocalDateTime = LocalDateTime.now(),

    /** 계정 정보 수정 시간 */
    @LastModifiedDate
    @Column(nullable = false)
    var updatedAt: LocalDateTime = LocalDateTime.now()
) {
    /**
     * 사용자의 비밀번호를 업데이트합니다.
     * @param newPassword 새로운 비밀번호
     * @param passwordEncoder 비밀번호 암호화를 위한 인코더
     * @throws IllegalArgumentException 비밀번호가 유효하지 않은 경우
     */
    fun changePassword(newPassword: String, passwordEncoder: PasswordEncoder) {
        validatePassword(newPassword)
        this.password = passwordEncoder.encode(newPassword)
        this.updatedAt = LocalDateTime.now()
    }

    /**
     * 사용자의 주소를 업데이트합니다.
     * @param newAddress 새로운 주소
     * @throws IllegalArgumentException 주소가 유효하지 않은 경우
     */
    fun changeAddress(newAddress: String) {
        validateAddress(newAddress)
        this.address = newAddress
        this.updatedAt = LocalDateTime.now()
    }

    /**
     * 비밀번호가 유효한지 검증합니다.
     * @param password 검증할 비밀번호
     * @throws IllegalArgumentException 비밀번호가 유효하지 않은 경우
     */
    private fun validatePassword(password: String) {
        if (password.length < 8 || password.length > 20) {
            throw IllegalArgumentException("비밀번호는 8자 이상 20자 이하로 입력해주세요")
        }
        if (!password.matches(Regex("^(?=.*[A-Za-z])(?=.*\\d)(?=.*[@$!%*#?&])[A-Za-z\\d@$!%*#?&]{8,}$"))) {
            throw IllegalArgumentException("비밀번호는 영문, 숫자, 특수문자를 포함해야 합니다")
        }
    }

    /**
     * 주소가 유효한지 검증합니다.
     * @param address 검증할 주소
     * @throws IllegalArgumentException 주소가 유효하지 않은 경우
     */
    private fun validateAddress(address: String) {
        if (address.isBlank()) {
            throw IllegalArgumentException("주소는 필수 입력값입니다")
        }
    }

    /**
     * 현재 비밀번호가 주어진 비밀번호와 일치하는지 확인합니다.
     * @param rawPassword 확인할 비밀번호
     * @param passwordEncoder 비밀번호 검증을 위한 인코더
     * @return 비밀번호가 일치하면 true, 아니면 false
     */
    fun isPasswordMatch(rawPassword: String, passwordEncoder: PasswordEncoder): Boolean {
        return passwordEncoder.matches(rawPassword, this.password)
    }

    /**
     * 사용자가 관리자인지 확인합니다.
     * @return 관리자이면 true, 아니면 false
     */
    fun isAdmin(): Boolean = role == UserRole.ADMIN

    companion object {
        /**
         * User 도메인의 빌더 클래스
         */
        fun builder() = Builder()
    }

    class Builder {
        private var email: String? = null
        private var password: String? = null
        private var name: String? = null
        private var phoneNumber: String? = null
        private var address: String? = null
        private var profileImageUrl: String? = null
        private var role: UserRole = UserRole.USER

        fun email(email: String) = apply { this.email = email }
        fun password(password: String) = apply { this.password = password }
        fun name(name: String) = apply { this.name = name }
        fun phoneNumber(phoneNumber: String) = apply { this.phoneNumber = phoneNumber }
        fun address(address: String) = apply { this.address = address }
        fun profileImageUrl(profileImageUrl: String?) = apply { this.profileImageUrl = profileImageUrl }
        fun role(role: UserRole) = apply { this.role = role }

        fun build(): User {
            requireNotNull(email) { "이메일은 필수 입력값입니다" }
            requireNotNull(password) { "비밀번호는 필수 입력값입니다" }
            requireNotNull(name) { "이름은 필수 입력값입니다" }
            requireNotNull(phoneNumber) { "전화번호는 필수 입력값입니다" }
            requireNotNull(address) { "주소는 필수 입력값입니다" }

            return User(
                email = email!!,
                password = password!!,
                name = name!!,
                phoneNumber = phoneNumber!!,
                address = address!!,
                profileImageUrl = profileImageUrl,
                role = role
            )
        }
    }
}