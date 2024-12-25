package rainbowfriends.daramserverv2.global.security.jwt.service.impl

import io.jsonwebtoken.Jwts
import io.jsonwebtoken.SignatureAlgorithm
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import rainbowfriends.daramserverv2.global.member.enums.Roles
import rainbowfriends.daramserverv2.global.redis.RedisUtil
import rainbowfriends.daramserverv2.global.security.jwt.service.JwtTokenService
import java.time.LocalDateTime
import java.time.ZoneId
import java.util.*

@Service
class JwtTokenServiceImpl(
    @Value("\${jwt.secret}") private val secretKey: String,
    @Value("\${jwt.access-token-validity}") private val accessTokenValidity: Long,
    @Value("\${jwt.refresh-token-validity}") private val refreshTokenValidity: Long,
    private val redisUtil: RedisUtil
) : JwtTokenService {
    private val key = Keys.hmacShaKeyFor(secretKey.toByteArray())

    override fun generateAccessToken(userId: String, role: Roles): Pair<String, LocalDateTime> {
        val expirationDate: LocalDateTime = LocalDateTime.now().plusSeconds(accessTokenValidity)
        val token = Jwts.builder()
            .setSubject(userId)
            .claim("role", role.name)
            .setIssuedAt(Date())
            .setExpiration(Date.from(expirationDate.atZone(ZoneId.of("Asia/Seoul")).toInstant()))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
        return Pair(token, expirationDate)
    }

    override fun generateRefreshToken(userId: String, role: Roles): Pair<String, LocalDateTime> {
        val expirationDate: LocalDateTime = LocalDateTime.now().plusSeconds(refreshTokenValidity)
        val token = Jwts.builder()
            .setSubject(userId)
            .claim("role", role.name)
            .setIssuedAt(Date())
            .setExpiration(Date.from(expirationDate.atZone(ZoneId.of("Asia/Seoul")).toInstant()))
            .signWith(key, SignatureAlgorithm.HS256)
            .compact()
        redisUtil.set(token, userId, refreshTokenValidity.toInt())
        return Pair(token, expirationDate)
    }
}