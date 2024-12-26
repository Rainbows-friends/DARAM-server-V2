package rainbowfriends.daramserverv2.global.security.jwt.service.impl

import org.springframework.stereotype.Service
import rainbowfriends.daramserverv2.domain.auth.exception.ReissueTokenException
import rainbowfriends.daramserverv2.global.member.enums.Roles
import rainbowfriends.daramserverv2.global.redis.RedisUtil
import rainbowfriends.daramserverv2.global.security.exception.ExpiredTokenException
import rainbowfriends.daramserverv2.global.security.exception.InvalidRefreshTokenException
import rainbowfriends.daramserverv2.global.security.exception.RegenerateTokenFailedException
import rainbowfriends.daramserverv2.global.security.jwt.service.JwtTokenParserService
import rainbowfriends.daramserverv2.global.security.jwt.service.JwtTokenRefreshService
import rainbowfriends.daramserverv2.global.security.jwt.service.JwtTokenService
import java.time.LocalDateTime
import java.util.*

@Service
class JwtTokenRefreshServiceImpl(
    private val jwtTokenService: JwtTokenService,
    private val jwtTokenParserService: JwtTokenParserService,
    private val redisUtil: RedisUtil
) : JwtTokenRefreshService {

    override fun saveRefreshToken(userId: String, refreshToken: String) {  // Refresh Token 저장
        redisUtil.set(refreshToken, userId, REFRESH_TOKEN_EXPIRATION_SECONDS)  // Redis에 Refresh Token 저장
    }

    override fun validateRefreshToken(refreshToken: String): Boolean {  // Refresh Token 유효성 검사
        if (!redisUtil.exists(refreshToken)) throw InvalidRefreshTokenException("Refresh Token is invalid")  // Redis에 Refresh Token이 존재하지 않는 경우 예외 발생
        if (!jwtTokenParserService.isTokenValid(refreshToken)) {  // Refresh Token이 유효하지 않은 경우
            redisUtil.delete(refreshToken)  // Redis에서 Refresh Token 삭제
            throw ExpiredTokenException("Refresh Token is expired")  // Refresh Token 만료 예외 발생
        }
        return true
    }

    override fun regenerateToken(userId: String, refreshToken: String): Pair<Pair<String, LocalDateTime>, Pair<String, LocalDateTime>> {  // 토큰 재발급
        if (validateRefreshToken(refreshToken)) {  // Refresh Token 유효성 검사
            val role: Roles = jwtTokenParserService.extractRole(refreshToken)  // Refresh Token에서 Role 추출
            val newAccessToken: Pair<String, LocalDateTime> = jwtTokenService.generateAccessToken(userId, role)  // Access Token 생성
            val newRefreshToken: Pair<String, LocalDateTime> = jwtTokenService.generateRefreshToken(userId, role)  // Refresh Token 생성
            redisUtil.delete(refreshToken)  // 기존 Refresh Token 삭제
            saveRefreshToken(userId, newRefreshToken.first)  // 새로운 Refresh Token 저장
            return Pair(  // 새로운 Access Token과 Refresh Token 반환
                Pair(newAccessToken.first, newAccessToken.second),
                Pair(newRefreshToken.first, newRefreshToken.second)
            )
        }
        throw RegenerateTokenFailedException("Token regeneration failed")  // 토큰 재발급 실패 시 예외 발생
    }

    companion object {  // Java에서 static 변수 선언
        private const val REFRESH_TOKEN_EXPIRATION_SECONDS = 604800  // Refresh Token 만료 시간(7일)
    }
}