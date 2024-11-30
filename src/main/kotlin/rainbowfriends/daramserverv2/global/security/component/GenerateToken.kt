package rainbowfriends.daramserverv2.global.security.component

import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import rainbowfriends.daramserverv2.global.member.enums.Roles
import rainbowfriends.daramserverv2.global.redis.RedisUtil
import rainbowfriends.daramserverv2.global.security.entity.Token
import java.nio.charset.StandardCharsets
import java.security.MessageDigest
import java.security.SecureRandom
import java.util.*

@Deprecated(message = "Use JwtTokenService instead")
@Component
class GenerateToken(
    private val redisUtil: RedisUtil,
    @Value("\${token.secret}")
    private val secret: String
) {
    private val random = SecureRandom()

    private fun generateRandomBase64String(): String {
        val randomBytes = ByteArray(24)
        random.nextBytes(randomBytes)
        return Base64.getUrlEncoder().withoutPadding().encodeToString(randomBytes)
    }

    private fun hashWithSecret(randomString: String, secret: String): String {
        val message = randomString + secret
        val digest = MessageDigest.getInstance("SHA-256")
        val hashBytes = digest.digest(message.toByteArray(StandardCharsets.UTF_8))
        return Base64.getUrlEncoder().withoutPadding().encodeToString(hashBytes)
    }

    fun generateToken(roles: Roles): String {
        var accessToken: String
        do {
            val randomBase64String = generateRandomBase64String()
            accessToken = hashWithSecret(randomBase64String, secret)
        } while (redisUtil.exists(accessToken))
        val token = Token(
            token = accessToken,
            role = roles,
            expiredAt = Date(System.currentTimeMillis() + 1000 * 60 * 60 * 24)
        )
        redisUtil.set(token.token, token, 60 * 60 * 24)
        return accessToken
    }
}