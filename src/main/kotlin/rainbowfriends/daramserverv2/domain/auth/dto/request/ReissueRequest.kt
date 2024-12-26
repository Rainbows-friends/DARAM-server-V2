package rainbowfriends.daramserverv2.domain.auth.dto.request

data class ReissueRequest(  // 토큰 재발급 요청 DTO
    val refreshToken: String  // 리프레시 토큰
)