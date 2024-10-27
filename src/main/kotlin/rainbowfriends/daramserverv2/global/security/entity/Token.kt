package rainbowfriends.daramserverv2.global.security.entity

import jakarta.persistence.Id
import org.springframework.data.redis.core.RedisHash
import rainbowfriends.daramserverv2.global.member.enums.Roles
import java.util.*

@RedisHash("token", timeToLive = 60 * 60 * 24)
data class Token(
    @Id val id: String = UUID.randomUUID().toString(),
    var token: String = "",
    var role: Roles = Roles.USER,
    var expiredAt: Date = Date()
)