package rainbowfriends.daramserverv2.global.security.token.Impl

import org.springframework.stereotype.Service
import rainbowfriends.daramserverv2.global.member.enums.Roles
import rainbowfriends.daramserverv2.global.security.component.GetTokenEntity
import rainbowfriends.daramserverv2.global.security.entity.Token
import rainbowfriends.daramserverv2.global.security.token.TokenAuthenticationService
import rainbowfriends.daramserverv2.global.security.token.TokenService

@Deprecated(message = "Use JwtTokenService instead")
@Service
class TokenAuthenticationServiceImpl(private val tokenService: TokenService,private val getTokenEntity: GetTokenEntity): TokenAuthenticationService {
    override fun getRoleByToken(token: String): Roles {
        return getTokenEntity.getTokenEntity(token).role
    }
}