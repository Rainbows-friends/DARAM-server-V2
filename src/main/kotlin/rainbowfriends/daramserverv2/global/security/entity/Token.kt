package rainbowfriends.daramserverv2.global.security.entity

import jakarta.persistence.Id
import org.springframework.data.redis.core.RedisHash
import rainbowfriends.daramserverv2.global.member.enums.Roles
import java.time.LocalDateTime

@RedisHash("token", timeToLive = 60 * 60 * 24)
data class Token(
    @Id val token: String,
    var role: Roles,
    var expiredAt: LocalDateTime
)