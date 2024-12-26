package rainbowfriends.daramserverv2.domain.auth.exception

class ReissueTokenException(message: String) : RuntimeException(message)  // 토큰 재발급 중 에러 발생 시 발행