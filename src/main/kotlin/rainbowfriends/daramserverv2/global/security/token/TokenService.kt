package rainbowfriends.daramserverv2.global.security.token

import rainbowfriends.daramserverv2.global.member.enums.Roles
import rainbowfriends.daramserverv2.global.security.dto.TokenResponse

interface TokenService {
    fun generateTokenDto(key: String, role: Roles): TokenResponse
    fun deleteToken(token: String)
    fun resolveToken(token: String): String
    fun decodeToken(token: String): Roles
    fun validateToken(token: String): Boolean
}