package rainbowfriends.daramserverv2.global.security.component

import org.springframework.stereotype.Component
import rainbowfriends.daramserverv2.global.redis.RedisUtil

@Component
class DeleteToken(private val redisUtil: RedisUtil) {
    fun deleteToken(token: String) {
        redisUtil.delete(token)
    }
}