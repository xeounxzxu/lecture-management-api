package io.xeounxzxu.lecturemanagementapi.domain

import io.xeounxzxu.lecturemanagementapi.domain.type.UserType
import jakarta.persistence.*
import org.hibernate.annotations.Comment

/**
 * 항목 설명
 * 이름 회원 이름
 * 이메일 회원 이메일
 * 휴대폰 번호 회원 휴대폰 번호
 * 회원 유형 수강생 / 강사
 */
@Entity(name = "users")
class UserEntity(
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_id")
    val id: Long? = null,
    @Comment("유저의 이름")
    @Column(name = "name", nullable = false)
    val name: String,
    @Column(name = "email", nullable = false, unique = true)
    @Comment("회원 이메일")
    val email: String,
    @Column(name = "phone_number", nullable = false)
    @Comment("회원의 휴대폰 번호")
    val phoneNumber: String,
    @Column(name = "encrypted_password", nullable = false)
    @Comment("로그인을 위한 비밀번호 (암호화)")
    val password: String,
    @Enumerated(EnumType.STRING)
    @Column(name = "user_type", nullable = false)
    @Comment("유저의 타입 (e.g.수강생, 강사)")
    val userType: UserType
)

