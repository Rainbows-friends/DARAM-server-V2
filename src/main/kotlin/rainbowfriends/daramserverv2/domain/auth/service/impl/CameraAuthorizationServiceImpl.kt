package rainbowfriends.daramserverv2.domain.auth.service.impl

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
        if (!findKey.findKey(key)) {  // false 반환 시 KeyNotFoundException 발생
            throw KeyNotFoundException("Key not found")
        }
        val accessToken = jwtTokenService.generateAccessToken("camera", Roles.ADMIN)  // ID = "camera", Role = ADMIN 으로 AccessToken 생성
        val refreshToken = jwtTokenService.generateRefreshToken("camera", Roles.ADMIN) // ID = "camera", Role = ADMIN 으로 RefreshToken 생성
        return SigninOrReissueResponse(
            accessToken = accessToken.first,  // accessToken Pair의 첫번째 값인 AccessToken 반환
            accessTokenExpiresIn = accessToken.second.toString(), // accessToken Pair의 두번째 값인 AccessToken 만료 시간 반환
            refreshToken = refreshToken.first,  // refreshToken Pair의 첫번째 값인 RefreshToken 반환
            refreshTokenExpiresIn = refreshToken.second.toString(),  // refreshToken Pair의 두번째 값인 RefreshToken 만료 시간 반환
            role = Roles.ADMIN
        )
    }
}