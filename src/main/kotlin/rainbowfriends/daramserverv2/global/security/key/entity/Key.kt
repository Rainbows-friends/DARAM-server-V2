package rainbowfriends.daramserverv2.global.security.key.entity

import jakarta.persistence.*
import rainbowfriends.daramserverv2.global.member.enums.Roles

@Entity
@Table(name = "key_table")
data class Key(
    @Id @Column(name = "`key`", unique = true)  // key는 DB 예약어이므로 ``로 감싸줌
    val key: String,
    @Column(name = "role", nullable = false)  // role은 null이 될 수 없음
    @Enumerated(EnumType.STRING)  // Enum 타입을 DB에 문자열로 저장
    val role: Roles
)