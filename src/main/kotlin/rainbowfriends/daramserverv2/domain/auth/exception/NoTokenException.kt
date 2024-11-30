package rainbowfriends.daramserverv2.domain.auth.exception

@Deprecated(message = "Use JwtTokenService instead")
class NoTokenException(message: String) : RuntimeException(message)