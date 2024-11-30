package rainbowfriends.daramserverv2.global.security.component

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.security.core.authority.SimpleGrantedAuthority
import org.springframework.stereotype.Component
import rainbowfriends.daramserverv2.global.redis.RedisUtil
import rainbowfriends.daramserverv2.global.security.entity.Token

@Deprecated(message = "Use JwtTokenService instead")
@Component
class DecodeToken(
    private val redisUtil: RedisUtil
) {
    fun decodeToken(token: String): UsernamePasswordAuthenticationToken {
        val tokenEntity = redisUtil.get(token) as Token
        val role = tokenEntity.role
        val authorities = listOf(SimpleGrantedAuthority("ROLE_${role.name}"))
        return UsernamePasswordAuthenticationToken(role, null, authorities)
    }
}