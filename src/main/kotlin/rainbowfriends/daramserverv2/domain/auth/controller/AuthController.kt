package rainbowfriends.daramserverv2.domain.auth.controller

import org.springframework.web.bind.annotation.*
import rainbowfriends.daramserverv2.domain.auth.dto.request.CameraAuthorizationRequest
import rainbowfriends.daramserverv2.domain.auth.dto.request.ReissueRequest
import rainbowfriends.daramserverv2.domain.auth.dto.request.SignInRequest
import rainbowfriends.daramserverv2.domain.auth.dto.response.SigninOrReissueResponse
import rainbowfriends.daramserverv2.domain.auth.service.CameraAuthorizationService
import rainbowfriends.daramserverv2.domain.auth.service.ReissueService
import rainbowfriends.daramserverv2.domain.auth.service.SignInService

@RequestMapping("/auth")
@RestController
class AuthController(
    private val signInService: SignInService,
    private val cameraAuthorizationService: CameraAuthorizationService,
    private val reissueService: ReissueService
) {
    @PostMapping("/signin")
    fun signIn(@RequestBody code: SignInRequest): SigninOrReissueResponse {
        return signInService.signIn(code.code)
    }

    @PostMapping("/camera/authorization")
    fun cameraAuthorization(@RequestBody key: CameraAuthorizationRequest): SigninOrReissueResponse {
        return cameraAuthorizationService.cameraAuthorization(key.key)
    }

    @PutMapping("/refresh")
    fun reissue(@RequestBody refreshToken: ReissueRequest): SigninOrReissueResponse {
        return reissueService.reissue(refreshToken.refreshToken)
    }
}