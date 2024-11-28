package rainbowfriends.daramserverv2.global.security.token

import rainbowfriends.daramserverv2.global.member.enums.Roles

@Deprecated(message = "Use JwtTokenService instead")
interface TokenAuthenticationService {
    fun getRoleByToken(token: String): Roles
}