package io.xeounxzxu.lecturemanagementapi.api.dto

import io.xeounxzxu.lecturemanagementapi.service.dto.UserLoginDto

data class UserLoginRequest(
    val email: String,
    val password: String
) {

    fun toDto(): UserLoginDto {
        return UserLoginDto(
            email = this.email,
            password = this.password
        )
    }
}
