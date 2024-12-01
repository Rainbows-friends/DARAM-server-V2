package rainbowfriends.daramserverv2.global.security.jwt.filter

import com.fasterxml.jackson.databind.ObjectMapper
import jakarta.servlet.FilterChain
import jakarta.servlet.http.HttpServletRequest
import jakarta.servlet.http.HttpServletResponse
import org.springframework.http.HttpStatus
import org.springframework.security.core.context.SecurityContextHolder
import org.springframework.util.StringUtils
import org.springframework.web.filter.OncePerRequestFilter
import rainbowfriends.daramserverv2.global.exception.dto.ErrorResponse
import rainbowfriends.daramserverv2.global.exception.dto.enums.ErrorStatus
import rainbowfriends.daramserverv2.global.security.exception.ExpiredTokenException
import rainbowfriends.daramserverv2.global.security.exception.InvalidTokenFormatException
import rainbowfriends.daramserverv2.global.security.jwt.service.JwtTokenParserService

class JwtFilter(
    private val jwtTokenParserService: JwtTokenParserService
) : OncePerRequestFilter() {

    override fun doFilterInternal(
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if(request.requestURI.startsWith("/api/v2/auth") || request.requestURI.startsWith("/api/v2/time") || request.requestURI.equals("/api/v2/actuator/health")) {
            filterChain.doFilter(request, response)
            return
        }
        val bearerToken = request.getHeader(AUTHORIZATION_HEADER)
        if (!StringUtils.hasText(bearerToken) || !bearerToken.startsWith(BEARER_PREFIX)) {
            setErrorResponse(response, "Missing or invalid Authorization header")
            return
        }
        val token = bearerToken.substring(BEARER_PREFIX.length)
        try {
            if (!jwtTokenParserService.isTokenValid(token)) throw ExpiredTokenException("Access Token is invalid or expired")
            val authentication = jwtTokenParserService.extractRole(token)
            SecurityContextHolder.getContext().authentication = authentication
            filterChain.doFilter(request, response)
        } catch (e: ExpiredTokenException) {
            setErrorResponse(response, e.message ?: "Token expired")
        } catch (e: InvalidTokenFormatException) {
            setErrorResponse(response, e.message ?: "Token format is invalid")
        } catch (e: Exception) {
            setErrorResponse(response, "Unexpected error: ${e.message}")
        }
    }

    private fun setErrorResponse(response: HttpServletResponse, errorMessage: String) {
        response.status = HttpStatus.UNAUTHORIZED.value()
        response.contentType = "application/json"
        val errorResponse = ErrorResponse(ErrorStatus.ERROR, errorMessage, HttpStatus.UNAUTHORIZED.value())
        response.writer.write(ObjectMapper().writeValueAsString(errorResponse))
    }

    companion object {
        private const val AUTHORIZATION_HEADER = "Authorization"
        private const val BEARER_PREFIX = "Bearer "
    }
}