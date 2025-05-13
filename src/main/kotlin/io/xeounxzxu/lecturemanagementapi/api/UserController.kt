package io.xeounxzxu.lecturemanagementapi.api

import io.xeounxzxu.lecturemanagementapi.api.dto.UserLoginRequest
import io.xeounxzxu.lecturemanagementapi.api.dto.UserLoginResponse
import io.xeounxzxu.lecturemanagementapi.api.dto.UserSignupRequest
import io.xeounxzxu.lecturemanagementapi.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.ResponseStatus
import org.springframework.web.bind.annotation.RestController

@RestController
class UserController(
    private val userService: UserService
) {

    @PostMapping("/v1/users")
    @ResponseStatus(HttpStatus.CREATED)
    fun signup(
        @Valid @RequestBody
        request: UserSignupRequest
    ) {
        userService.signup(
            dto = request.toDto()
        )
    }

    @PostMapping("/v1/login")
    fun login(
        @RequestBody
        request: UserLoginRequest
    ): UserLoginResponse {
        val data = userService.login(request.toDto())
        return UserLoginResponse(
            token = data
        )
    }
}
