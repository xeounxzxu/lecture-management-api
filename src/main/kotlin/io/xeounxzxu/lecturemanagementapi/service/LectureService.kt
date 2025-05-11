package io.xeounxzxu.lecturemanagementapi.service

import io.xeounxzxu.lecturemanagementapi.domain.LectureEntityRepository
import io.xeounxzxu.lecturemanagementapi.domain.type.UserType
import io.xeounxzxu.lecturemanagementapi.service.dto.LectureCreatedDto
import org.springframework.stereotype.Service

@Service
class LectureService(
    private val lectureEntityRepository: LectureEntityRepository
) {

    fun creatLecture(
        dto: LectureCreatedDto
    ) {

        // 강사가 아니면 예외 처리
        check(UserType.TEACHER == dto.userType) { "회원 정보를 확인 해주세요." }

        // 강의 등록
        lectureEntityRepository.save(
            dto.toSaveInitialEntity()
        )
    }
}
