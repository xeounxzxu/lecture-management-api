package io.xeounxzxu.lecturemanagementapi.api

import io.xeounxzxu.lecturemanagementapi.api.dto.UserLoginRequest
import io.xeounxzxu.lecturemanagementapi.api.dto.UserLoginResponse
import io.xeounxzxu.lecturemanagementapi.api.dto.UserSignupRequest
import io.xeounxzxu.lecturemanagementapi.service.UserService
import jakarta.validation.Valid
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*

@RestController
@RequestMapping("/api/users")
class UserController(
    private val userService: UserService
) {

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    fun signup(
        @Valid @RequestBody
        request: UserSignupRequest
    ) {
        userService.signup(
            dto = request.toDto()
        )
    }

    @PostMapping("/login")
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
