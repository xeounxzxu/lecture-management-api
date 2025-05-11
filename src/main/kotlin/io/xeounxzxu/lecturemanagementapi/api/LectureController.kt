package io.xeounxzxu.lecturemanagementapi.api

import io.xeounxzxu.lecturemanagementapi.api.dto.LectureCreatedRequest
import io.xeounxzxu.lecturemanagementapi.config.dto.UserArgument
import io.xeounxzxu.lecturemanagementapi.service.LectureService
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/v1/lectures")
class LectureController(
    private val lectureService: LectureService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun created(
        @RequestBody request: LectureCreatedRequest,
        userArgument: UserArgument
    ) {
        lectureService.creatLecture(
            request.toDto(
                userId = userArgument.userId,
                userType = userArgument.userType,
            )
        )
    }
}
