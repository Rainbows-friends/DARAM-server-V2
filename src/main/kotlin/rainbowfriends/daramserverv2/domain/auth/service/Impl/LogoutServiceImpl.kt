package rainbowfriends.daramserverv2.domain.auth.service.Impl

import jakarta.servlet.http.HttpServletRequest
import org.springframework.stereotype.Service
import rainbowfriends.daramserverv2.domain.auth.exception.NoTokenException
import rainbowfriends.daramserverv2.domain.auth.service.LogoutService
import rainbowfriends.daramserverv2.global.security.token.TokenService

@Deprecated(message = "Use JwtTokenService instead")
@Service
class LogoutServiceImpl(
    private val tokenService: TokenService
) : LogoutService {
    override fun logout(request: HttpServletRequest) {
        tokenService.deleteToken(
            request.getHeader("Authorization") ?: throw NoTokenException(
                "No Token"
            )
        )
    }
}