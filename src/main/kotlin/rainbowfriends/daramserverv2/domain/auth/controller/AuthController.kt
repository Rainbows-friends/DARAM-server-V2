package rainbowfriends.daramserverv2.domain.auth.controller

import org.springframework.validation.annotation.Validated
import org.springframework.web.bind.annotation.*
import rainbowfriends.daramserverv2.domain.auth.dto.request.CameraAuthorizationRequest
import rainbowfriends.daramserverv2.domain.auth.dto.request.ReissueRequest
import rainbowfriends.daramserverv2.domain.auth.dto.request.SignInRequest
import rainbowfriends.daramserverv2.domain.auth.dto.response.SigninOrReissueResponse
import rainbowfriends.daramserverv2.domain.auth.service.CameraAuthorizationService
import rainbowfriends.daramserverv2.domain.auth.service.ReissueService
import rainbowfriends.daramserverv2.domain.auth.service.SignInService
import rainbowfriends.daramserverv2.domain.auth.service.ValidateTokenService

@RequestMapping("/auth")
@RestController
class AuthController(
    private val signInService: SignInService,
    private val cameraAuthorizationService: CameraAuthorizationService,
    private val reissueService: ReissueService,
    private val validateTokenService: ValidateTokenService
) {
    @PostMapping("/signin")  // POST /auth/signin
    fun signIn(@RequestBody @Validated code: SignInRequest): SigninOrReissueResponse {
        return signInService.signIn(code.code) // 로그인을 시도하는 코드를 받아 로그인을 시도하고 결과를 반환
    }

    @PostMapping("/camera/authorization")  // POST /auth/camera/authorization
    fun cameraAuthorization(@RequestBody @Validated key: CameraAuthorizationRequest): SigninOrReissueResponse {
        return cameraAuthorizationService.cameraAuthorization(key.key)  // 카메라 인증을 시도하는 키를 받아 인증을 시도하고 결과를 반환
    }

    @PutMapping("/refresh")  // PUT /auth/refresh
    fun reissue(@RequestBody @Validated refreshToken: ReissueRequest): SigninOrReissueResponse {
        return reissueService.reissue(refreshToken.refreshToken)  // 리프레시 토큰을 받아 새로운 토큰을 발급하고 결과를 반환
    }

    @GetMapping("/token/validate")  // GET /auth/token/validate
    fun validateToken(@RequestHeader("Authorization") authorization: String): Boolean {
        return validateTokenService.validateToken(authorization.removePrefix("Bearer ").trim())  // 토큰을 받아 토큰이 유효한지 확인하고 결과를 반환
    }
}