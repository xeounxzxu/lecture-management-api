package io.xeounxzxu.lecturemanagementapi.exception

import org.springframework.http.HttpStatus

class BusinessException(
    val status : HttpStatus,
    message: String,
) : RuntimeException(
    message
)
