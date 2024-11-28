package rainbowfriends.daramserverv2.global.security.component

import org.springframework.stereotype.Component
import rainbowfriends.daramserverv2.domain.auth.exception.TokenNotFoundException
import rainbowfriends.daramserverv2.global.redis.RedisUtil

@Deprecated(message = "Not used anymore")
@Component
class DeleteToken(private val redisUtil: RedisUtil) {
    fun deleteToken(token: String) {
        if (redisUtil.exists(token)) {
            redisUtil.delete(token)
        } else {
            throw TokenNotFoundException("Token Not Found")
        }
    }
}