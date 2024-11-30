package rainbowfriends.daramserverv2.global.security.jwt.service

import io.jsonwebtoken.Claims
import rainbowfriends.daramserverv2.global.member.enums.Roles

interface JwtTokenParserService {
    fun extractUserId(token: String): String
    fun extractRole(token: String): Roles
    fun isTokenValid(token: String): Boolean
    fun parseClaims(token: String): Claims
}