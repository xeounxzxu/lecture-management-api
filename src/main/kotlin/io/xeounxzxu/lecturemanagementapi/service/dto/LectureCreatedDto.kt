package io.xeounxzxu.lecturemanagementapi.service.dto

import io.xeounxzxu.lecturemanagementapi.domain.LectureEntity
import io.xeounxzxu.lecturemanagementapi.domain.type.UserType

data class LectureCreatedDto(
    val title: String,
    val maxCapacity: Int,
    val price: Int,
    val userId: Long,
    val userType: UserType
) {

    fun toSaveInitialEntity(
    ): LectureEntity {
        return LectureEntity(
            id = null,
            title = this.title,
            userId = this.userId,
            maxCapacity = this.maxCapacity,
            currentEnrollCount = 0,
            price = this.price,
        )
    }
}
