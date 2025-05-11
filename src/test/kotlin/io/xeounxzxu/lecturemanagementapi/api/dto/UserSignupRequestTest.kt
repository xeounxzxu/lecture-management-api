package io.xeounxzxu.lecturemanagementapi.api.dto

import io.xeounxzxu.lecturemanagementapi.domain.type.UserType
import jakarta.validation.Validation
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class UserSignupRequestTest {

    private val validator = Validation.buildDefaultValidatorFactory().validator


    @Test
    fun `정상적인 요청은 유효성 검증을 통과한다`() {
        val request = UserSignupRequest(
            name = "홍길동",
            email = "hong@example.com",
            phoneNumber = "010-1234-5678",
            password = "Pass123",
            userType = UserType.STUDENT
        )

        val violations = validator.validate(request)
        assertEquals(0, violations.size, "유효성 검증 오류가 없어야 합니다.")
    }

    @Test
    fun `이름이 비어있으면 검증에 실패한다`() {
        val request = UserSignupRequest(
            name = "",
            email = "hong@example.com",
            phoneNumber = "010-1234-5678",
            password = "Pass123",
            userType = UserType.STUDENT
        )

        val violations = validator.validate(request)
        assertEquals(1, violations.size)
        assertEquals("이름을 잘못 적었어요. 다시 확인 해주세요.", violations.first().message)
    }

    @Test
    fun `이메일 형식이 잘못되면 검증에 실패한다`() {
        val request = UserSignupRequest(
            name = "홍길동",
            email = "invalid-email",
            phoneNumber = "010-1234-5678",
            password = "Pass123",
            userType = UserType.TEACHER
        )

        val violations = validator.validate(request)
        assertEquals(1, violations.size)
        assertEquals("이메일을 잘못 적었어요. 다시 확인 해주세요.", violations.first().message)
    }

    @Test
    fun `전화번호 형식이 잘못되면 검증에 실패한다`() {
        val request = UserSignupRequest(
            name = "홍길동",
            email = "hong@example.com",
            phoneNumber = "01012345678", // 하이픈 없음
            password = "Pass123",
            userType = UserType.STUDENT
        )

        val violations = validator.validate(request)
        assertEquals(1, violations.size)
        assertEquals("전화번호는 010-0000-0000 형식으로 입력해야 합니다. 다시 확인 해주세요.", violations.first().message)
    }

    @Test
    fun `비밀번호 조합이 잘못되면 검증에 실패한다`() {
        val request = UserSignupRequest(
            name = "홍길동",
            email = "hong@example.com",
            phoneNumber = "010-1234-5678",
            password = "abcdef", // 소문자만 사용
            userType = UserType.STUDENT
        )

        val violations = validator.validate(request)
        assertEquals(1, violations.size)
        assertEquals("비빌번호를 잘못 입력 하였습니다. 다시 확인 해주세요.", violations.first().message)
    }
}
