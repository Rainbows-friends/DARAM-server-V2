package rainbowfriends.daramserverv2.domain.auth.dto.request

import org.jetbrains.annotations.NotNull

data class SignInRequest(  // 로그인 요청 DTO
    @field:NotNull val code: String  // 인증 코드
)