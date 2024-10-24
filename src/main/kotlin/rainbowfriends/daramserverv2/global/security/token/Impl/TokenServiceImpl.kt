package rainbowfriends.daramserverv2.global.security.token.Impl

import org.springframework.stereotype.Service
import rainbowfriends.daramserverv2.global.exception.TokenFormatException
import rainbowfriends.daramserverv2.global.member.enums.Roles
import rainbowfriends.daramserverv2.global.security.component.DecodeToken
import rainbowfriends.daramserverv2.global.security.component.DeleteToken
import rainbowfriends.daramserverv2.global.security.component.GenerateToken
import rainbowfriends.daramserverv2.global.security.component.ValidateToken
import rainbowfriends.daramserverv2.global.security.dto.TokenResponse
import rainbowfriends.daramserverv2.global.security.token.TokenService

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

    override fun resolveToken(token: String): String {
        if (token.startsWith("Bearer ")) {
            return token.substring(7)
        } else {
            return token
        }
    }

    override fun decodeToken(token: String): Roles {
        return decodeToken.decodeToken(token)
    }

    override fun validateToken(token: String): Boolean {
        return validateToken.validateToken(token)
    }
}