package io.xeounxzxu.lecturemanagementapi.config

import io.xeounxzxu.lecturemanagementapi.config.dto.UserArgument
import io.xeounxzxu.lecturemanagementapi.domain.TokenEntityRepository
import io.xeounxzxu.lecturemanagementapi.domain.UserEntityRepository
import jakarta.servlet.http.HttpServletRequest
import org.springframework.core.MethodParameter
import org.springframework.web.bind.support.WebDataBinderFactory
import org.springframework.web.context.request.NativeWebRequest
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.method.support.ModelAndViewContainer
import kotlin.jvm.optionals.getOrNull


class UserArgumentResolver(
    private val tokenEntityRepository: TokenEntityRepository,
    private val userEntityRepository: UserEntityRepository
) : HandlerMethodArgumentResolver {
    override fun supportsParameter(parameter: MethodParameter): Boolean {
        return parameter.getParameterType() == UserArgument::class.java
    }

    override fun resolveArgument(
        parameter: MethodParameter,
        mavContainer: ModelAndViewContainer?,
        webRequest: NativeWebRequest,
        binderFactory: WebDataBinderFactory?
    ): UserArgument {

        val httpServletRequest = webRequest.nativeRequest as HttpServletRequest

        // TODO : 헤더 예외처리 확인
        val authorization =
            httpServletRequest.getHeader("Authorization") ?: throw RuntimeException("header check")

        if (authorization.startsWith(AUTHORIZATION_PREFIX).not()) {
            throw RuntimeException("header check")
        }

        val token = authorization.removePrefix(AUTHORIZATION_PREFIX).trim()

        // 토큰에 매핑된 userId 값을 가지고 온다.
        val tokenEntity = tokenEntityRepository.findByToken(token = token) ?: throw RuntimeException("token check")

        // 유저의 정보를 가지고 온다.
        val user = userEntityRepository.findById(
            tokenEntity.userId
        ).getOrNull() ?: throw RuntimeException("user check")

        return UserArgument(
            userId = user.id!!,
            userType = user.userType
        )
    }

    companion object {
        private const val AUTHORIZATION_PREFIX = "Bearer "
    }
}
