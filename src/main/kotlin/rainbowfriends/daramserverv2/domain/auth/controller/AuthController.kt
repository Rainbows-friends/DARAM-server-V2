package rainbowfriends.daramserverv2.domain.auth.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import rainbowfriends.daramserverv2.domain.auth.dto.AuthorizeAdminRequest
import rainbowfriends.daramserverv2.domain.auth.dto.request.ReissueRequest
import rainbowfriends.daramserverv2.domain.auth.dto.request.SignInRequest
import rainbowfriends.daramserverv2.domain.auth.dto.response.SigninOrReissueResponse
import rainbowfriends.daramserverv2.domain.auth.service.AdminAuthorizationService
import rainbowfriends.daramserverv2.domain.auth.service.LogoutService
import rainbowfriends.daramserverv2.domain.auth.service.ReissueService
import rainbowfriends.daramserverv2.domain.auth.service.SignInService
import rainbowfriends.daramserverv2.global.security.dto.TokenResponse

@RequestMapping("/auth")
@RestController
@Tag(name = "Auth", description = "관리자 인증 API")
class AuthController(
    private val adminAuthorizationService: AdminAuthorizationService,
    private val logoutService: LogoutService,
    private val signInService: SignInService,
    private val reissueService: ReissueService
) {
    @Operation(summary = "관리자 인증", description = "관리자 권한을 인가합니다")
    @PostMapping
    @Deprecated(message = "Google OAuth2 Authorization use")
    fun authorizeAdmin(@RequestBody key: AuthorizeAdminRequest): TokenResponse {
        return adminAuthorizationService.authorizeAdmin(key.key)
    }

    @Operation(summary = "로그아웃", description = "로그아웃합니다")
    @DeleteMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @Deprecated(message = "Google OAuth2 Authorization use")
    fun logout(request: HttpServletRequest) {
        logoutService.logout(request)
    }

    @Operation(summary = "로그인", description = "로그인합니다")
    @PostMapping("/signin")
    fun signIn(@RequestBody code: SignInRequest): SigninOrReissueResponse {
        return signInService.signIn(code.code)
    }

    @Operation(summary = "토근 갱신", description = "토큰을 갱신합니다")
    @PutMapping("/refresh")
    fun reissue(@RequestBody refreshToken: ReissueRequest): SigninOrReissueResponse {
        return reissueService.reissue(refreshToken.refreshToken)
    }
}