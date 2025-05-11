package io.xeounxzxu.lecturemanagementapi.api.dto

import io.xeounxzxu.lecturemanagementapi.domain.type.UserType
import io.xeounxzxu.lecturemanagementapi.service.dto.LectureCreatedDto

data class LectureCreatedRequest(
    val title: String,
    val maxCapacity: Int,
    val price: String
) {

    private val parsedPrice: Int
        get() =
            requireNotNull(runCatching { price.replace(",", "").toInt() }.getOrNull())

    fun toDto(
        userId: Long,
        userType: UserType
    ): LectureCreatedDto {
        return LectureCreatedDto(
            title = title,
            maxCapacity = maxCapacity,
            price = parsedPrice,
            userId = userId,
            userType = userType
        )
    }
}
