package rainbowfriends.daramserverv2.domain.auth.dto

@Deprecated(message = "Use JwtTokenService instead")
data class AuthorizeAdminRequest(
    val key: String
)