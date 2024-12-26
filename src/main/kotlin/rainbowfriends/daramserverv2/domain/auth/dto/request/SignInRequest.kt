package rainbowfriends.daramserverv2.domain.auth.dto.request

data class SignInRequest(  // 로그인 요청 DTO
    val code: String  // 인증 코드
)
