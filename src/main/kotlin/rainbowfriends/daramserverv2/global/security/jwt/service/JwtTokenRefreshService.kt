package rainbowfriends.daramserverv2.global.security.jwt.service

import java.util.*

interface JwtTokenRefreshService {
    fun saveRefreshToken(userId: String, refreshToken: String)
    fun validateRefreshToken(refreshToken: String): Boolean
    fun regenerateToken(userId: String, refreshToken: String): Pair<Pair<String, Date>, Pair<String, Date>>
}