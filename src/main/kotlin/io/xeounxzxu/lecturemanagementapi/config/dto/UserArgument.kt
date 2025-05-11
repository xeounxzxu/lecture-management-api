package io.xeounxzxu.lecturemanagementapi.config.dto

import io.xeounxzxu.lecturemanagementapi.domain.type.UserType

data class UserArgument(
    val userId: Long,
    val userType: UserType
)
