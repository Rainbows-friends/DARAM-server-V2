package rainbowfriends.daramserverv2.global.security.filter

import jakarta.servlet.*
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.util.StringUtils
import rainbowfriends.daramserverv2.global.security.token.TokenService
import java.io.IOException

class TokenFilter(private val tokenService: TokenService) : Filter {
    @Throws(IOException::class, ServletException::class)
    override fun doFilter(request: ServletRequest?, response: ServletResponse?, chain: FilterChain?) {
        val httpRequest = request as HttpServletRequest
        val httpResponse = response as HttpServletResponse
        if (httpRequest.requestURI.startsWith("/api/v2/auth") ||
            httpRequest.requestURI.startsWith("/api/v2/time") ||
            httpRequest.requestURI.startsWith("/api/v2/graphql") ||
            httpRequest.requestURI.startsWith("/api/v2/member") ||
            httpRequest.requestURI == "/api/v2/checkin/checkin" ||
            httpRequest.requestURI == "/api/v2/checkin/uncheckin"
        ) {
            chain!!.doFilter(request, response)
            return
        }
        val authorizationHeader = httpRequest.getHeader("Authorization")
        if (StringUtils.hasText(authorizationHeader) && authorizationHeader.startsWith("Bearer ")) {
            val token = authorizationHeader.substring(7)
            if (tokenService.validateToken(token)) {
                chain!!.doFilter(request, response)
                return
            }
        }
        httpResponse.sendError(HttpServletResponse.SC_UNAUTHORIZED)
    }

    override fun init(filterConfig: FilterConfig?) {}
    override fun destroy() {}
}