package io.xeounxzxu.lecturemanagementapi.api.dto

import io.xeounxzxu.lecturemanagementapi.domain.type.UserType
import io.xeounxzxu.lecturemanagementapi.service.dto.UserSignupDto
import jakarta.validation.constraints.Email
import jakarta.validation.constraints.NotBlank
import jakarta.validation.constraints.Pattern

/**
 * 비밀번호 로그인을 위한 비밀번호
 *  - 최소 6자 이상 10자 이하
 *  - 영문 소문자, 대문자, 숫자 중 최소 두 가지 이상 조합 필요
 */
data class UserSignupRequest(
    @field:NotBlank(message = "이름을 잘못 적었어요. 다시 확인 해주세요.")
    val name: String,
    @field:Email(message = "이메일을 잘못 적었어요. 다시 확인 해주세요.")
    val email: String,
    @field:Pattern(
        regexp = "^01[016789]-\\d{3,4}-\\d{4}$",
        message = "전화번호는 010-0000-0000 형식으로 입력해야 합니다. 다시 확인 해주세요."
    )
    val phoneNumber: String,
    @field:Pattern(
        regexp = "^(?=(?:.*[A-Z].*[a-z]|.*[a-z].*[A-Z]|.*[A-Z].*\\d|.*\\d.*[A-Z]|.*[a-z].*\\d|.*\\d.*[a-z]))[A-Za-z\\d]{6,10}$",
        message = "비빌번호를 잘못 입력 하였습니다. 다시 확인 해주세요."
    )
    val password: String,
    val userType: UserType
) {

    fun toDto(): UserSignupDto {
        return UserSignupDto(
            name = this.name,
            email = this.email,
            phoneNumber = this.phoneNumber,
            password = this.password,
            userType = this.userType
        )
    }
}
