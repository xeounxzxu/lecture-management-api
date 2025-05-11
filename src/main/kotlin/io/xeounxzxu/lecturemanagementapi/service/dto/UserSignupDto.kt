package io.xeounxzxu.lecturemanagementapi.service.dto

import io.xeounxzxu.lecturemanagementapi.domain.UserEntity
import io.xeounxzxu.lecturemanagementapi.domain.type.UserType

data class UserSignupDto(
    val name: String,
    val email: String,
    val phoneNumber: String,
    val password: String,
    val userType: UserType
) {


    fun toEntity(
        encodedPassword: String
    ): UserEntity {
        return UserEntity(
            name = name,
            email = email,
            phoneNumber = phoneNumber,
            password = encodedPassword,
            userType = userType
        )
    }
}
