package io.xeounxzxu.lecturemanagementapi.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.xeounxzxu.lecturemanagementapi.domain.UserEntityRepository
import io.xeounxzxu.lecturemanagementapi.domain.type.UserType
import io.xeounxzxu.lecturemanagementapi.exception.BusinessException
import io.xeounxzxu.lecturemanagementapi.mock.UserEntityMock
import io.xeounxzxu.lecturemanagementapi.service.dto.UserSignupDto
import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test
import org.junit.jupiter.api.assertDoesNotThrow
import org.junit.jupiter.api.assertThrows
import org.springframework.security.crypto.password.PasswordEncoder


class UserServiceTest {

    private val userEntityRepository: UserEntityRepository = mockk()
    private val passwordEncoder: PasswordEncoder = mockk()

    private val sut = UserService(
        userEntityRepository = userEntityRepository,
        passwordEncoder = passwordEncoder
    )

    @Test
    fun `동일한 이메일로는 가입이 불가능`() {

        val entity = UserEntityMock.entity()
        every {
            userEntityRepository.findByEmail(any())
        } returns entity

        val dto = mockDto()

        val ex = assertThrows<BusinessException> {
            sut.signup(dto)
        }

        verify(exactly = 1) {
            userEntityRepository.findByEmail(any())
        }
        verify(exactly = 0) {
            passwordEncoder.encode(any())
        }
        verify(exactly = 0) {
            userEntityRepository.save(any())
        }

        Assertions.assertEquals(ex.message, "이미 존재하는 이메일 입니다. 다시 확인 해주세요.")
    }

    @Test
    fun `유저는 정상적으로 가입을 한다`() {

        val entity = UserEntityMock.entity()
        val password = "test"
        every {
            userEntityRepository.findByEmail(any())
        } returns null
        every {
            passwordEncoder.encode(any())
        } returns password
        every {
            userEntityRepository.save(any())
        } returns entity

        val dto = mockDto()
        assertDoesNotThrow {
            sut.signup(dto)
        }

        verify(exactly = 1) {
            userEntityRepository.findByEmail(any())
        }
        verify(exactly = 1) {
            passwordEncoder.encode(any())
        }
        verify(exactly = 1) {
            userEntityRepository.save(any())
        }
    }

    companion object {
        private fun mockDto(
            name: String = "테스트1",
            email: String = "test@naver.com",
            phoneNumber: String = "010-0000-0000",
            password: String = "123123123",
            userType: UserType = UserType.TEACHER
        ): UserSignupDto {
            return UserSignupDto(
                name = name,
                email = email,
                phoneNumber = phoneNumber,
                password = password,
                userType = userType,
            )
        }
    }
}
