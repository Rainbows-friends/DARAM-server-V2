package rainbowfriends.daramserverv2.domain.auth.service

import rainbowfriends.daramserverv2.global.security.dto.TokenResponse

@Deprecated(message = "Use JwtTokenService instead")
interface AdminAuthorizationService {
    fun authorizeAdmin(key: String): TokenResponse
}