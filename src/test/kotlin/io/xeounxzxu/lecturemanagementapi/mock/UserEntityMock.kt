package io.xeounxzxu.lecturemanagementapi.mock

import io.xeounxzxu.lecturemanagementapi.domain.UserEntity
import io.xeounxzxu.lecturemanagementapi.domain.type.UserType

object UserEntityMock {

    fun entity(
        id: Long? = 1,
        name: String = "테스트1",
        email: String = "test@naver.com",
        phoneNumber: String = "010-0000-0000",
        password: String = "123123123",
        userType: UserType = UserType.TEACHER
    ): UserEntity {
        return UserEntity(
            id = id,
            name = name,
            email = email,
            phoneNumber = phoneNumber,
            password = password,
            userType = userType,
        )
    }
}
