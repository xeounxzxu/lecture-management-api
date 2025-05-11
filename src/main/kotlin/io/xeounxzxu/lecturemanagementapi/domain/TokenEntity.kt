package io.xeounxzxu.lecturemanagementapi.domain

import jakarta.persistence.*
import org.hibernate.annotations.Comment

@Entity(name = "tokens")
class TokenEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "token_id")
    val id: Long? = null,

    @Column(name = "token", nullable = false, unique = true)
    @Comment("토큰값")
    val token: String,

    @Column(nullable = false)
    @Comment("유저 아이디")
    val userId: Long
)
