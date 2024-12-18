package rainbowfriends.daramserverv2.domain.auth.service.Impl

import org.springframework.stereotype.Service
import rainbowfriends.daramserverv2.domain.auth.component.FindKey
import rainbowfriends.daramserverv2.domain.auth.dto.response.SigninOrReissueResponse
import rainbowfriends.daramserverv2.domain.auth.exception.KeyNotFoundException
import rainbowfriends.daramserverv2.domain.auth.service.CameraAuthorizationService
import rainbowfriends.daramserverv2.global.member.enums.Roles
import rainbowfriends.daramserverv2.global.security.jwt.service.JwtTokenService

@Service
class CameraAuthorizationServiceImpl(
    private val jwtTokenService: JwtTokenService,
    private val findKey: FindKey
) : CameraAuthorizationService {
    override fun cameraAuthorization(key: String): SigninOrReissueResponse {
        if (!findKey.findKey(key)) {
            throw KeyNotFoundException("Key not found")
        }
        val accessToken = jwtTokenService.generateAccessToken("camera", Roles.ADMIN)
        val refreshToken = jwtTokenService.generateRefreshToken("camera", Roles.ADMIN)
        return SigninOrReissueResponse(
            accessToken = accessToken.first,
            accessTokenExpiresIn = accessToken.second.toInstant().toString(),
            refreshToken = refreshToken.first,
            refreshTokenExpiresIn = refreshToken.second.toInstant().toString(),
            role = Roles.ADMIN
        )
    }
}