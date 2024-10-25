package rainbowfriends.daramserverv2.domain.notice.entity

import jakarta.persistence.*
import jakarta.persistence.Entity
import rainbowfriends.daramserverv2.global.member.enums.Roles
import java.time.LocalDateTime

@Entity
@Table(name = "notice")
data class Notice(
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    val id: Long?,
    @Column(nullable = false)
    var title: String,
    @Column(nullable = false)
    var context: String,
    @Column(nullable = false)
    var author: Roles,
    @Column(nullable = false)
    var createdAt: LocalDateTime,
)