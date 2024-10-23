package rainbowfriends.daramserverv2.domain.auth.service

import rainbowfriends.daramserverv2.global.security.dto.TokenResponse

interface AdminAuthorizationService {
    fun authorizeAdmin(key: String): TokenResponse
}