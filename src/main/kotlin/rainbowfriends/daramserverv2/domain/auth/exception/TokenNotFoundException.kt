package rainbowfriends.daramserverv2.domain.auth.exception

@Deprecated(message = "Use JwtTokenService instead")
class TokenNotFoundException(message: String) : RuntimeException(message)