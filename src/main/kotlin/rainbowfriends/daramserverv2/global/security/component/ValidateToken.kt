package rainbowfriends.daramserverv2.global.security.component

import jakarta.transaction.Transactional
import org.springframework.stereotype.Component
import rainbowfriends.daramserverv2.global.redis.RedisUtil

@Deprecated(message = "Use JwtTokenService instead")
@Component
@Transactional
class ValidateToken(private val redisUtil: RedisUtil) {
    fun validateToken(token: String): Boolean {
        return redisUtil.exists(token)
    }
}