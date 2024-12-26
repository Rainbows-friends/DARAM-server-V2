package rainbowfriends.daramserverv2.global.security.jwt.service.impl

import io.jsonwebtoken.Claims
import io.jsonwebtoken.security.Keys
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import rainbowfriends.daramserverv2.global.member.enums.Roles
import rainbowfriends.daramserverv2.global.security.exception.InvalidTokenFormatException
import rainbowfriends.daramserverv2.global.security.jwt.service.JwtTokenParserService

@Service
class JwtTokenParserServiceImpl(@Value("\${jwt.secret}") private val secretKey: String) : JwtTokenParserService {
    private val key = Keys.hmacShaKeyFor(secretKey.toByteArray())  // secretKey를 바이트 배열로 변환하여 key로 사용

    override fun extractUserId(token: String): String {  // token에서 userId 추출
        return parseClaims(token).subject  // token에서 subject를 추출하여 반환(=userId)
    }

    override fun extractRole(token: String): Roles {  // token에서 Role 추출
        val claims = parseClaims(token)  // token을 파싱하여 claims로 변환
        val roleValue = claims["role"]?.toString()  // claims에서 role을 추출하여 문자열로 변환
        return try {
            Roles.valueOf(roleValue ?: throw IllegalArgumentException("Role is null"))  // roleValue가 null이면 IllegalArgumentException 발생
        } catch (_: IllegalArgumentException) {  // IllegalArgumentException 발생 시
            throw InvalidTokenFormatException("Invalid role in token: $roleValue")
        }
    }

    override fun isTokenValid(token: String): Boolean {  // token이 유효한지 확인
        return try {
            parseClaims(token)  // token을 파싱하여 claims로 변환
            true
        } catch (e: Exception) {  // 파싱 중 예외 발생 시
            false
        }
    }

    override fun parseClaims(token: String): Claims {  // token을 파싱하여 claims로 변환
        return io.jsonwebtoken.Jwts.parserBuilder()  // Jwts.parserBuilder()로 파서 생성
            .setSigningKey(key)  // key를 설정
            .build()  // 파서 생성
            .parseClaimsJws(token)  // token을 파싱하여 Jws로 변환
            .body  // Jws에서 body를 추출하여 반환
    }
}