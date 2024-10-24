package rainbowfriends.daramserverv2.domain.auth.controller

import io.swagger.v3.oas.annotations.Operation
import io.swagger.v3.oas.annotations.tags.Tag
import jakarta.servlet.http.HttpServletRequest
import org.springframework.http.HttpStatus
import org.springframework.web.bind.annotation.*
import rainbowfriends.daramserverv2.domain.auth.dto.AuthorizeAdminRequest
import rainbowfriends.daramserverv2.domain.auth.service.AdminAuthorizationService
import rainbowfriends.daramserverv2.domain.auth.service.LogoutService
import rainbowfriends.daramserverv2.global.security.dto.TokenResponse

@RequestMapping("/auth")
@RestController
@Tag(name = "Auth", description = "관리자 인증 API")
class AuthController(
    private val adminAuthorizationService: AdminAuthorizationService,
    private val logoutService: LogoutService
) {
    @Operation(summary = "관리자 인증", description = "관리자 권한을 인가합니다")
    @PostMapping
    fun authorizeAdmin(@RequestBody key: AuthorizeAdminRequest): TokenResponse {
        return adminAuthorizationService.authorizeAdmin(key.key)
    }

    @Operation(summary = "로그아웃", description = "로그아웃합니다")
    @DeleteMapping
    @ResponseStatus(code = HttpStatus.NO_CONTENT)
    fun logout(request: HttpServletRequest) {
        logoutService.logout(request)
    }
}