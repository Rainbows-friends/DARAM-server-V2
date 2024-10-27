package rainbowfriends.daramserverv2.global.security.token

import rainbowfriends.daramserverv2.global.member.enums.Roles

interface TokenAuthenticationService {
    fun getRoleByToken(token: String): Roles
}