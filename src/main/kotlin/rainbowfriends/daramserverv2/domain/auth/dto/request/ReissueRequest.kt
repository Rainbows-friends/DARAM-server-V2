package rainbowfriends.daramserverv2.domain.auth.dto.request

import org.jetbrains.annotations.NotNull

data class ReissueRequest(  // 토큰 재발급 요청 DTO
    @field:NotNull val refreshToken: String  // 리프레시 토큰
)