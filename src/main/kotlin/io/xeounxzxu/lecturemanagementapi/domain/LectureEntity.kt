package io.xeounxzxu.lecturemanagementapi.domain

import jakarta.persistence.*
import org.hibernate.annotations.Comment
import org.springframework.data.annotation.CreatedDate
import org.springframework.data.annotation.LastModifiedDate
import java.time.LocalDateTime

@Entity(name = "lecture")
class LectureEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "lecture_id")
    val id: Long? = null,

    @Column(name = "title", nullable = false)
    @Comment("강의명")
    val title: String,

    @Column(name = "user_id", nullable = false)
    @Comment("강사의 유저 아이디")
    val userId: Long,

    @Column(name = "max_capacity", nullable = false)
    @Comment("최대 정원")
    val maxCapacity: Int,

    @Column(name = "current_enroll_count", nullable = false)
    @Comment("현재 정원")
    val currentEnrollCount: Int,

    @Column(name = "price", nullable = false)
    @Comment("강의 가격 (원 단위)")
    val price: Int,

    @CreatedDate
    val createdDateTime: LocalDateTime? = null,

    @LastModifiedDate
    val updatedDateTime: LocalDateTime? = null,
)
