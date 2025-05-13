package io.xeounxzxu.lecturemanagementapi.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface TokenEntityRepository : JpaRepository<TokenEntity, Long> {
    fun findByToken(token: String): TokenEntity?
    fun findByUserId(userId: Long): TokenEntity?
}
