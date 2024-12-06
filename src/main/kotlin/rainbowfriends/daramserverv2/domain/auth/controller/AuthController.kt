package rainbowfriends.daramserverv2.domain.auth.controller

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
class AuthController(
    private val adminAuthorizationService: AdminAuthorizationService,
    private val logoutService: LogoutService,
    private val signInService: SignInService,
    private val reissueService: ReissueService
) {
    @PostMapping
    @Deprecated(message = "Google OAuth2 Authorization use")
    fun authorizeAdmin(@RequestBody key: AuthorizeAdminRequest): TokenResponse {
        return adminAuthorizationService.authorizeAdmin(key.key)
    }

    @DeleteMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    @Deprecated(message = "Google OAuth2 Authorization use")
    fun logout(request: HttpServletRequest) {
        logoutService.logout(request)
    }

    @PostMapping("/signin")
    fun signIn(@RequestBody code: SignInRequest): SigninOrReissueResponse {
        return signInService.signIn(code.code)
    }

    @PutMapping("/refresh")
    fun reissue(@RequestBody refreshToken: ReissueRequest): SigninOrReissueResponse {
        return reissueService.reissue(refreshToken.refreshToken)
    }
}