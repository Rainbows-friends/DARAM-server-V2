package rainbowfriends.daramserverv2.domain.auth.service

import jakarta.servlet.http.HttpServletRequest

interface LogoutService {
    fun logout(request: HttpServletRequest)
}