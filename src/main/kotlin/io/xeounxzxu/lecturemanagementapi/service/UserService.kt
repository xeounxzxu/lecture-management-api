package io.xeounxzxu.lecturemanagementapi.service

import io.xeounxzxu.lecturemanagementapi.domain.TokenEntity
import io.xeounxzxu.lecturemanagementapi.domain.TokenEntityRepository
import io.xeounxzxu.lecturemanagementapi.domain.UserEntityRepository
import io.xeounxzxu.lecturemanagementapi.exception.BusinessException
import io.xeounxzxu.lecturemanagementapi.service.dto.UserLoginDto
import io.xeounxzxu.lecturemanagementapi.service.dto.UserSignupDto
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import java.util.*

@Service
class UserService(
    private val userEntityRepository: UserEntityRepository,
    private val passwordEncoder: PasswordEncoder,
    private val tokenEntityRepository: TokenEntityRepository
) {

    private fun validateUser(email: String) {
        val entity = userEntityRepository.findByEmail(email)
        if (entity != null) {
            throw BusinessException(
                HttpStatus.BAD_REQUEST,
                "이미 존재하는 이메일입니다. 다시 확인해주세요."
            )
        }
    }

    fun signup(dto: UserSignupDto) {
        validateUser(dto.email)
        val encodedPassword = passwordEncoder.encode(dto.password)

        userEntityRepository.save(
            dto.toEntity(encodedPassword = encodedPassword)
        )
    }

    fun login(dto: UserLoginDto): String {
        val user = userEntityRepository.findByEmail(dto.email)
            ?: throw BusinessException(
                HttpStatus.BAD_REQUEST,
                "이메일과 비밀번호를 확인해주세요."
            )

        if (!passwordEncoder.matches(dto.password, user.password)) {
            throw BusinessException(
                HttpStatus.BAD_REQUEST,
                "이메일과 비밀번호를 확인해주세요."
            )
        }

        return generateOrGetToken(user.id!!)
    }

    private fun generateOrGetToken(userId: Long): String {
        val tokenEntity = tokenEntityRepository.findByUserId(userId)
            ?: tokenEntityRepository.save(
                TokenEntity(
                    id = null,
                    token = UUID.randomUUID().toString(),
                    userId = userId
                )
            )

        return tokenEntity.token
    }
}
