package io.xeounxzxu.lecturemanagementapi.service

import io.xeounxzxu.lecturemanagementapi.domain.UserEntityRepository
import io.xeounxzxu.lecturemanagementapi.exception.BusinessException
import io.xeounxzxu.lecturemanagementapi.service.dto.UserSignupDto
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service

@Service
class UserService(
    private val userEntityRepository: UserEntityRepository,
    private val passwordEncoder: PasswordEncoder
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
}
