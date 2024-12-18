package rainbowfriends.daramserverv2.global.security.token.Impl

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken
import org.springframework.stereotype.Service
import rainbowfriends.daramserverv2.global.member.enums.Roles
import rainbowfriends.daramserverv2.global.security.component.DecodeToken
import rainbowfriends.daramserverv2.global.security.component.DeleteToken
import rainbowfriends.daramserverv2.global.security.component.GenerateToken
import rainbowfriends.daramserverv2.global.security.component.ValidateToken
import rainbowfriends.daramserverv2.global.security.dto.TokenResponse
import rainbowfriends.daramserverv2.global.security.token.TokenService

@Deprecated(message = "Use JwtTokenService instead")
@Service
class TokenServiceImpl(
    private val generateToken: GenerateToken,
    private val decodeToken: DecodeToken,
    private val validateToken: ValidateToken,
    private val deleteToken: DeleteToken
) : TokenService {
    override fun generateTokenDto(key: String, role: Roles): TokenResponse {
        return TokenResponse(generateToken.generateToken(role))
    }

    override fun deleteToken(token: String): Unit {
        deleteToken.deleteToken(token)
    }

    override fun decodeToken(token: String): UsernamePasswordAuthenticationToken {
        return decodeToken.decodeToken(token)
    }

    override fun validateToken(token: String): Boolean {
        return validateToken.validateToken(token)
    }
}