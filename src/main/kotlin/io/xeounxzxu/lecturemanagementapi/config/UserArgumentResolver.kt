package io.xeounxzxu.lecturemanagementapi.config

import io.xeounxzxu.lecturemanagementapi.config.dto.UserArgument
import io.xeounxzxu.lecturemanagementapi.domain.TokenEntityRepository
import io.xeounxzxu.lecturemanagementapi.domain.UserEntityRepository
import io.xeounxzxu.lecturemanagementapi.exception.BusinessException
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.MethodParameter
import org.springframework.http.HttpStatus
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer


class UserArgumentResolver(
    private val tokenEntityRepository: TokenEntityRepository,
    private val userEntityRepository: UserEntityRepository
) : HandlerMethodArgumentResolver {

    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.parameterType == UserArgument::class.java
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): UserArgument {

        val request = webRequest.nativeRequest as HttpServletRequest
        val token = extractTokenFromHeader(request)

        val tokenEntity = tokenEntityRepository.findByToken(token)
            ?: throw BusinessException(HttpStatus.UNAUTHORIZED, INVALID_TOKEN_MESSAGE)

        val user = userEntityRepository.findById(tokenEntity.userId)
            .orElseThrow { BusinessException(HttpStatus.UNAUTHORIZED, INVALID_TOKEN_MESSAGE) }

        return UserArgument(
            userId = user.id ?: throw BusinessException(HttpStatus.INTERNAL_SERVER_ERROR, "잘못된 사용자 정보입니다."),
            userType = user.userType
        )
    }

    private fun extractTokenFromHeader(request: HttpServletRequest): String {
        val authorizationHeader = request.getHeader(AUTH_HEADER)
            ?: throw BusinessException(HttpStatus.UNAUTHORIZED, MISSING_AUTH_HEADER_MESSAGE)

        if (!authorizationHeader.startsWith(AUTHORIZATION_PREFIX)) {
            throw BusinessException(HttpStatus.UNAUTHORIZED, INVALID_TOKEN_FORMAT_MESSAGE)
        }

        return authorizationHeader.removePrefix(AUTHORIZATION_PREFIX).trim()
    }

    companion object {
        private const val AUTH_HEADER = "Authorization"
        private const val AUTHORIZATION_PREFIX = "Bearer "
        private const val MISSING_AUTH_HEADER_MESSAGE = "인증 정보가 누락되었습니다. 헤더를 확인해주세요."
        private const val INVALID_TOKEN_FORMAT_MESSAGE = "인증 토큰 형식이 잘못되었습니다."
        private const val INVALID_TOKEN_MESSAGE = "유효하지 않은 인증 토큰입니다. 다시 로그인 해주세요."
    }
}
