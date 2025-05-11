package io.xeounxzxu.lecturemanagementapi.domain

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository

@Repository
interface UserEntityRepository : JpaRepository<UserEntity, Long> {

    fun findByEmail(email: String): UserEntity?
}
