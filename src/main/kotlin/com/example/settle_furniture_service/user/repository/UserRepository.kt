package com.example.settle_furniture_service.user.repository

import com.example.settle_furniture_service.user.domain.User
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

/**
 * User 도메인을 위한 JPA Repository 인터페이스
 */
@Repository
interface UserRepository : JpaRepository<User, Long> {
    /**
     * 이메일로 사용자를 조회합니다.
     * @param email 조회할 사용자의 이메일
     * @return 사용자 도메인 또는 null
     */
    fun findByEmail(email: String): User?

    /**
     * 이메일이 이미 존재하는지 확인합니다.
     * @param email 확인할 이메일
     * @return 이메일이 존재하면 true, 아니면 false
     */
    fun existsByEmail(email: String): Boolean
}