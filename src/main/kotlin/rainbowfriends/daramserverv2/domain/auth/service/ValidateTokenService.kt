package rainbowfriends.daramserverv2.domain.auth.service

interface ValidateTokenService {
    fun validateToken(token: String): Boolean
}