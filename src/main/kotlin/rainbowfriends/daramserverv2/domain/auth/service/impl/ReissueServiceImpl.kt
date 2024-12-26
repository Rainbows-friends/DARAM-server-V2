package rainbowfriends.daramserverv2.domain.auth.service.impl

import org.springframework.stereotype.Service
import rainbowfriends.daramserverv2.domain.auth.dto.response.SigninOrReissueResponse
import rainbowfriends.daramserverv2.domain.auth.exception.ReissueTokenException
import rainbowfriends.daramserverv2.domain.auth.service.ReissueService
import rainbowfriends.daramserverv2.domain.member.exception.MemberNotFoundException
import rainbowfriends.daramserverv2.global.member.component.FindMember
import rainbowfriends.daramserverv2.global.security.jwt.service.JwtTokenParserService
import rainbowfriends.daramserverv2.global.security.jwt.service.JwtTokenRefreshService
import java.time.LocalDateTime

@Service
class ReissueServiceImpl(
    private val jwtTokenRefreshService: JwtTokenRefreshService,
    private val jwtTokenParserService: JwtTokenParserService,
    private val findMember: FindMember
) : ReissueService {
    override fun reissue(refreshToken: String): SigninOrReissueResponse {
        if (jwtTokenRefreshService.validateRefreshToken(refreshToken)) {
            val userId: String = jwtTokenParserService.extractUserId(refreshToken)
            val newRefreshToken: Pair<Pair<String, LocalDateTime>, Pair<String, LocalDateTime>> =
                jwtTokenRefreshService.regenerateToken(userId, refreshToken)
            return SigninOrReissueResponse(
                accessToken = newRefreshToken.first.first,
                refreshToken = newRefreshToken.second.first,
                accessTokenExpiresIn = newRefreshToken.first.second.toString(),
                refreshTokenExpiresIn = newRefreshToken.second.second.toString(),
                role = findMember.findMemberByEmail(userId)?.role
                    ?: throw MemberNotFoundException("Member Not Found")
            )
        } else {
            throw ReissueTokenException("Invalid Refresh Token")
        }
    }
}