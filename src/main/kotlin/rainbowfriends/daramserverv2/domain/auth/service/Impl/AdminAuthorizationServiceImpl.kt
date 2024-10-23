package rainbowfriends.daramserverv2.domain.auth.service.Impl

import org.springframework.stereotype.Service
import rainbowfriends.daramserverv2.domain.auth.service.AdminAuthorizationService
import rainbowfriends.daramserverv2.global.exception.InvalidKeyException
import rainbowfriends.daramserverv2.global.security.dto.TokenResponse
import rainbowfriends.daramserverv2.global.security.key.component.GetKeyOfRole
import rainbowfriends.daramserverv2.global.security.key.component.VerifyKey
import rainbowfriends.daramserverv2.global.security.token.TokenService

@Service
class AdminAuthorizationServiceImpl(
    private val tokenService: TokenService,
    private val verifyKey: VerifyKey,
    private val getKeyOfRole: GetKeyOfRole
) : AdminAuthorizationService {
    override fun authorizeAdmin(key: String): TokenResponse {
        if (verifyKey.verifyKey(key)) {
            return tokenService.generateTokenDto(key, getKeyOfRole.getKeyOfRole(key))
        }
        else {
            throw InvalidKeyException("Invalid Key")
        }
    }
}