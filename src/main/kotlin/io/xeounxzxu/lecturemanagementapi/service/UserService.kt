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

    private fun validateUser(
        email: String,
    ) {
        // 필요한 검증 로직
        val entity = userEntityRepository.findByEmail(
            email = email
        )

        require(entity == null) {
            throw BusinessException(
                HttpStatus.BAD_REQUEST,
                "이미 존재하는 이메일 입니다. 다시 확인 해주세요."
            )
        }
    }

    fun signup(
        dto: UserSignupDto
    ) {

        // 가입 조건 체크
        validateUser(email = dto.email)

        // 비밀번호 암호화
        val encodedPassword = passwordEncoder.encode(dto.password)

        // 유저 생성
        userEntityRepository.save(
            dto.toEntity(
                encodedPassword = encodedPassword
            )
        )
    }

    fun login(
        dto: UserLoginDto
    ): String {

        val user = userEntityRepository.findByEmail(dto.email) ?: throw BusinessException(
            status = HttpStatus.BAD_REQUEST,
            "이메일과 비밀번호를 확인해주세요."
        )

        val encodedPassword = passwordEncoder.encode(dto.password)

        if ((encodedPassword == user.password).not()) {
            throw BusinessException(
                status = HttpStatus.BAD_REQUEST,
                "이메일과 비밀번호를 확인해주세요."
            )
        }

        val tokenEntity = tokenEntityRepository.save(
            TokenEntity(
                id = null,
                token = UUID.randomUUID().toString(),
                userId = checkNotNull(user.id)
            )
        )

        return tokenEntity.token
    }
}
