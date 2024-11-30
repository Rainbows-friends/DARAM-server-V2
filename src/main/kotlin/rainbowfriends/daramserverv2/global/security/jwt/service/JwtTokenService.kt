package rainbowfriends.daramserverv2.global.security.jwt.service

import rainbowfriends.daramserverv2.global.member.enums.Roles
import java.util.*

interface JwtTokenService {
    fun generateAccessToken(userId: String, role: Roles): Pair<String, Date>
    fun generateRefreshToken(userId: String, role: Roles): Pair<String, Date>
}