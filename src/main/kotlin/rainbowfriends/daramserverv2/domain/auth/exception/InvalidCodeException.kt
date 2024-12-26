package rainbowfriends.daramserverv2.domain.auth.exception

class InvalidCodeException(message: String) : RuntimeException(message)  // 인증 코드가 Google OAuth2에서 발행한 코드와 일치하지 않을 경우 발행