package io.xeounxzxu.lecturemanagementapi.service

import io.mockk.every
import io.mockk.mockk
import io.mockk.verify
import io.xeounxzxu.lecturemanagementapi.domain.LectureEntity
import io.xeounxzxu.lecturemanagementapi.domain.LectureEntityRepository
import io.xeounxzxu.lecturemanagementapi.domain.type.UserType
import io.xeounxzxu.lecturemanagementapi.service.dto.LectureCreatedDto
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertThrows
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test

class LectureServiceTest {

    private val lectureRepository = mockk<LectureEntityRepository>()
    private lateinit var lectureService: LectureService

    @BeforeEach
    fun setup() {
        lectureService = LectureService(lectureRepository)
    }

    @Test
    fun `강의 등록 성공`() {
        // Given
        val dto = LectureCreatedDto(
            title = "Kotlin 심화 강의", maxCapacity = 100, price = 50000, userId = 1, userType = UserType.TEACHER
        )
        every { lectureRepository.save(any()) } returns mockk<LectureEntity>()

        // When
        lectureService.creatLecture(dto)

        // Then
        verify { lectureRepository.save(any()) }
    }

    @Test
    fun `강의 등록 실패 - 강사가 아님`() {
        // Given
        val dto = LectureCreatedDto(
            title = "Kotlin 심화 강의", maxCapacity = 100, price = 50000, userId = 1, userType = UserType.STUDENT // 강사 아님
        )

        // When & Then
        val exception = assertThrows(IllegalStateException::class.java) {
            lectureService.creatLecture(dto)
        }
        assertEquals("회원 정보를 확인 해주세요.", exception.message)
        verify(exactly = 0) { lectureRepository.save(any()) }
    }
}
