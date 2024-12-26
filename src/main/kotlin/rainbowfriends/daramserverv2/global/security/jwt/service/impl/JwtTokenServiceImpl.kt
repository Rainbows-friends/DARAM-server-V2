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
    private val key = Keys.hmacShaKeyFor(secretKey.toByteArray())  // 키 생성

    override fun generateAccessToken(userId: String, role: Roles): Pair<String, LocalDateTime> {  // 액세스 토큰 생성
        val expirationDate: LocalDateTime = LocalDateTime.now().plusSeconds(accessTokenValidity)  // 만료 시간 설정
        val token = Jwts.builder()
            .setSubject(userId)  // 사용자 ID 설정(Subject에 userId 설정)
            .claim("role", role.name)  // 권한 설정(Claim에 role이라는 이름으로 권한 설정)
            .setIssuedAt(Date())  // 발급 시간 설정
            .setExpiration(Date.from(expirationDate.atZone(ZoneId.of("Asia/Seoul")).toInstant()))  // 만료 시간 설정(서울 기준)
            .signWith(key, SignatureAlgorithm.HS256)  // 서명 설정(HS256 알고리즘 사용)
            .compact()  // 토큰 생성
        return Pair(token, expirationDate)
    }

    override fun generateRefreshToken(userId: String, role: Roles): Pair<String, LocalDateTime> {  // 리프레시 토큰 생성
        val expirationDate: LocalDateTime = LocalDateTime.now().plusSeconds(refreshTokenValidity)  // 만료 시간 설정
        val token = Jwts.builder()  // 토큰 생성
            .setSubject(userId)  // 사용자 ID 설정
            .claim("role", role.name)  // 권한 설정
            .setIssuedAt(Date())  // 발급 시간 설정
            .setExpiration(Date.from(expirationDate.atZone(ZoneId.of("Asia/Seoul")).toInstant()))  // 만료 시간 설정
            .signWith(key, SignatureAlgorithm.HS256)  // 서명 설정
            .compact()
        redisUtil.set(token, userId, refreshTokenValidity.toInt())  // Redis에 토큰 저장
        return Pair(token, expirationDate)
    }
}