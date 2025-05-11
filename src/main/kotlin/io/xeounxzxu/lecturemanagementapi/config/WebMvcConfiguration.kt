package io.xeounxzxu.lecturemanagementapi.config

import io.xeounxzxu.lecturemanagementapi.domain.TokenEntityRepository
import io.xeounxzxu.lecturemanagementapi.domain.UserEntityRepository
import org.springframework.context.annotation.Configuration
import org.springframework.web.method.support.HandlerMethodArgumentResolver
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer

@Configuration
class WebMvcConfiguration(
    private val tokenEntityRepository: TokenEntityRepository,
    private val userEntityRepository: UserEntityRepository
) : WebMvcConfigurer {
    override fun addArgumentResolvers(resolvers: MutableList<HandlerMethodArgumentResolver>) {
        resolvers.add(
            UserArgumentResolver(
                tokenEntityRepository,
                userEntityRepository
            )
        )
    }
}
