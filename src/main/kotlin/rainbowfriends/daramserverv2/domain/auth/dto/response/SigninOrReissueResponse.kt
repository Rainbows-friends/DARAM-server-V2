package rainbowfriends.daramserverv2.domain.auth.dto.response

import rainbowfriends.daramserverv2.global.member.enums.Roles

data class SigninOrReissueResponse(  // 로그인 또는 토큰 재발급 응답
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpiresIn: String,
    val refreshTokenExpiresIn: String,
    val role: Roles
)