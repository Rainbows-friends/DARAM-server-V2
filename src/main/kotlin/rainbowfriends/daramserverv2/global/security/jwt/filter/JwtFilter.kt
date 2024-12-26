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
) : OncePerRequestFilter() {  // OncePerRequestFilter(OAuth2AuthenticationProcessingFilter)를 상속받아 필터 구현

    override fun doFilterInternal(  // 필터 로직
        request: HttpServletRequest,
        response: HttpServletResponse,
        filterChain: FilterChain
    ) {
        if(request.requestURI.startsWith("/api/v2/auth") || request.requestURI.startsWith("/api/v2/time") || request.requestURI.equals("/api/v2/actuator/health")) {  // /api/v2/auth, /api/v2/time, /api/v2/actuator/health는 필터 제외
            filterChain.doFilter(request, response)
            return
        }
        val bearerToken = request.getHeader(AUTHORIZATION_HEADER)  // Authorization 헤더에서 Bearer Token 추출
        if (!StringUtils.hasText(bearerToken) || !bearerToken.startsWith(BEARER_PREFIX)) {  // Bearer Token이 없거나 형식이 잘못된 경우
            setErrorResponse(response, "Missing or invalid Authorization header")  // 에러 응답 반환
            return
        }
        val token = bearerToken.substring(BEARER_PREFIX.length)  // Bearer Token에서 Token 부분만 추출
        try {
            if (!jwtTokenParserService.isTokenValid(token)) throw ExpiredTokenException("Access Token is invalid or expired")  // Token이 유효하지 않은 경우 예외 발생
            val authentication = jwtTokenParserService.extractRole(token)  // Token에서 권한 추출
            SecurityContextHolder.getContext().authentication = authentication  // Spring Security Context에 권한 설정
            filterChain.doFilter(request, response)  // 다음 필터로 전달
        } catch (e: ExpiredTokenException) {  // 만료된 Token인 경우
            setErrorResponse(response, e.message ?: "Token expired")
        } catch (e: InvalidTokenFormatException) {  // 잘못된 Token 형식인 경우
            setErrorResponse(response, e.message ?: "Token format is invalid")
        } catch (e: Exception) {  // 그 외 예외 발생 시
            setErrorResponse(response, "Unexpected error: ${e.message}")
        }
    }

    private fun setErrorResponse(response: HttpServletResponse, errorMessage: String) {  // 에러 응답 반환(ExceptionHandler는 Controller에서만 사용 가능)
        response.status = HttpStatus.UNAUTHORIZED.value()  // 상태 코드 401(Unauthorized) 설정
        response.contentType = "application/json"  // Content-Type 설정
        val errorResponse = ErrorResponse(ErrorStatus.ERROR, errorMessage, HttpStatus.UNAUTHORIZED.value())  // 에러 응답 DTO 생성
        response.writer.write(ObjectMapper().writeValueAsString(errorResponse))  // 응답 반환
    }

    companion object {  // Java static과 같은 기능
        private const val AUTHORIZATION_HEADER = "Authorization"  // Authorization 헤더 이름
        private const val BEARER_PREFIX = "Bearer "  // Bearer Token 접두사
    }
}