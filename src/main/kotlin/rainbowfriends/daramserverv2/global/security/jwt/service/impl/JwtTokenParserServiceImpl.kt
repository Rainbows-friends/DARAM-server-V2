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
    private val key = Keys.hmacShaKeyFor(secretKey.toByteArray())

    override fun extractUserId(token: String): String {
        return parseClaims(token).subject
    }

    override fun extractRole(token: String): Roles {
        val claims = parseClaims(token)
        val roleValue = claims["role"]?.toString()
        return try {
            Roles.valueOf(roleValue ?: throw IllegalArgumentException("Role is null"))
        } catch (_: IllegalArgumentException) {
            throw InvalidTokenFormatException("Invalid role in token: $roleValue")
        }
    }

    override fun isTokenValid(token: String): Boolean {
        return try {
            parseClaims(token)
            true
        } catch (e: Exception) {
            false
        }
    }

    override fun parseClaims(token: String): Claims {
        return io.jsonwebtoken.Jwts.parserBuilder()
            .setSigningKey(key)
            .build()
            .parseClaimsJws(token)
            .body
    }
}