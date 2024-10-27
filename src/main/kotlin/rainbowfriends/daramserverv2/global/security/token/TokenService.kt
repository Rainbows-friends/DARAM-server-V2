package rainbowfriends.daramserverv2.global.security.token

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import rainbowfriends.daramserverv2.global.member.enums.Roles
import rainbowfriends.daramserverv2.global.security.dto.TokenResponse

interface TokenService {
    fun generateTokenDto(key: String, role: Roles): TokenResponse
    fun deleteToken(token: String)
    fun decodeToken(token: String): UsernamePasswordAuthenticationToken
    fun validateToken(token: String): Boolean
}