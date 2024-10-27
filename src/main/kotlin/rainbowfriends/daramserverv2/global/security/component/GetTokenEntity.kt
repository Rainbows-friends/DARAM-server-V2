package rainbowfriends.daramserverv2.global.security.component

import org.springframework.stereotype.Component
import rainbowfriends.daramserverv2.global.redis.RedisUtil
import rainbowfriends.daramserverv2.global.security.entity.Token

@Component
class GetTokenEntity(private val redisUtil: RedisUtil) {
    fun getTokenEntity(token: String): Token {
        return redisUtil.get(token) as Token
    }
}