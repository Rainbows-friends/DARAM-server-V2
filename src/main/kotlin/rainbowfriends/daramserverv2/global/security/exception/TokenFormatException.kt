package rainbowfriends.daramserverv2.global.security.exception

@Deprecated(message = "Use JwtTokenService instead")
class TokenFormatException(message: String) : RuntimeException(message)