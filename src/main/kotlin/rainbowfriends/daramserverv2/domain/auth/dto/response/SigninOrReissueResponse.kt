package rainbowfriends.daramserverv2.domain.auth.dto.response

import rainbowfriends.daramserverv2.global.member.enums.Roles

data class SigninOrReissueResponse(
    val accessToken: String,
    val refreshToken: String,
    val accessTokenExpiresIn: String,
    val refreshTokenExpiresIn: String,
    val role: Roles
)