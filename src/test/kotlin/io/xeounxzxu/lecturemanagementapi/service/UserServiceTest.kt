import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.xeounxzxu.lecturemanagementapi.domain.TokenEntity
import io.xeounxzxu.lecturemanagementapi.domain.TokenEntityRepository
import io.xeounxzxu.lecturemanagementapi.domain.UserEntity
import io.xeounxzxu.lecturemanagementapi.domain.UserEntityRepository
import io.xeounxzxu.lecturemanagementapi.domain.type.UserType
import io.xeounxzxu.lecturemanagementapi.exception.BusinessException
import io.xeounxzxu.lecturemanagementapi.service.UserService
import io.xeounxzxu.lecturemanagementapi.service.dto.UserLoginDto
import io.xeounxzxu.lecturemanagementapi.service.dto.UserSignupDto
import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.http.HttpStatus
import org.springframework.security.crypto.password.PasswordEncoder

class UserServiceTest {

    private val userRepository = mockk<UserEntityRepository>()
    private val passwordEncoder = mockk<PasswordEncoder>()
    private val tokenRepository = mockk<TokenEntityRepository>()
    private lateinit var userService: UserService

    @BeforeEach
    fun setup() {
        userService = UserService(userRepository, passwordEncoder, tokenRepository)
    }

    @Test
    fun `회원가입 성공`() {
        // Given
        val dto = UserSignupDto("홍길동", "hong@example.com", "password123", "000000000", UserType.STUDENT)
        every { userRepository.findByEmail(dto.email) } returns null
        every { passwordEncoder.encode(dto.password) } returns "encodedPassword"
        every { userRepository.save(any()) } returns mockk()

        // When
        userService.signup(dto)

        // Then
        verify { userRepository.findByEmail(dto.email) }
        verify { passwordEncoder.encode(dto.password) }
        verify { userRepository.save(any()) }
    }

    @Test
    fun `회원가입 실패 - 이미 존재하는 이메일`() {
        // Given
        val dto = UserSignupDto("홍길동", "hong@example.com", "password123", "000000000", UserType.STUDENT)
        every { userRepository.findByEmail(dto.email) } returns mockk()

        // When & Then
        val exception = assertThrows(BusinessException::class.java) {
            userService.signup(dto)
        }
        assertEquals(HttpStatus.BAD_REQUEST, exception.status)
        assertEquals("이미 존재하는 이메일입니다. 다시 확인해주세요.", exception.message)
    }

    @Test
    fun `로그인 성공 - 토큰 신규 생성`() {
        // Given
        val dto = UserLoginDto("hong@example.com", "password123")
        val user = UserEntity(1L, "홍길동", "hong@example.com", "encodedPassword", "010-1234-5678", UserType.STUDENT)
        every { userRepository.findByEmail(dto.email) } returns user
        every { passwordEncoder.matches(dto.password, user.password) } returns true
        every { tokenRepository.findByUserId(user.id!!) } returns null
        every { tokenRepository.save(any()) } answers { firstArg<TokenEntity>() }

        // When
        val token = userService.login(dto)

        // Then
        assertTrue(token.isNotBlank())
        verify { userRepository.findByEmail(dto.email) }
        verify { passwordEncoder.matches(dto.password, user.password) }
        verify { tokenRepository.findByUserId(user.id!!) }
        verify { tokenRepository.save(any()) }
    }

    @Test
    fun `로그인 실패 - 이메일 없음`() {
        // Given
        val dto = UserLoginDto("notfound@example.com", "password123")
        every { userRepository.findByEmail(dto.email) } returns null

        // When & Then
        val exception = assertThrows(BusinessException::class.java) {
            userService.login(dto)
        }
        assertEquals(HttpStatus.BAD_REQUEST, exception.status)
        assertEquals("이메일과 비밀번호를 확인해주세요.", exception.message)
    }

    @Test
    fun `로그인 실패 - 비밀번호 불일치`() {
        // Given
        val dto = UserLoginDto("hong@example.com", "wrongPassword")
        val user = UserEntity(1L, "홍길동", "hong@example.com", "encodedPassword", "010-1234-5678", UserType.STUDENT)
        every { userRepository.findByEmail(dto.email) } returns user
        every { passwordEncoder.matches(dto.password, user.password) } returns false

        // When & Then
        val exception = assertThrows(BusinessException::class.java) {
            userService.login(dto)
        }
        assertEquals(HttpStatus.BAD_REQUEST, exception.status)
        assertEquals("이메일과 비밀번호를 확인해주세요.", exception.message)
    }
}
