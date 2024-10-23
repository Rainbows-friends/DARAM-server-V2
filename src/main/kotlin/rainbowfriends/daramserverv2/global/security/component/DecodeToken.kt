package rainbowfriends.daramserverv2.global.security.component

import org.springframework.stereotype.Component
import rainbowfriends.daramserverv2.global.member.enums.Roles
import rainbowfriends.daramserverv2.global.redis.RedisUtil
import rainbowfriends.daramserverv2.global.security.entity.Token

@Component
class DecodeToken(
    private val redisUtil: RedisUtil
) {
    fun decodeToken(token: String): Roles {
        val tokenEntity = redisUtil.get(token) as Token
        return tokenEntity.role
    }
}