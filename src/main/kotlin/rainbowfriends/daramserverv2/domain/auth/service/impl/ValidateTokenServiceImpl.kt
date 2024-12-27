package rainbowfriends.daramserverv2.domain.auth.service.impl

import org.springframework.stereotype.Service
import rainbowfriends.daramserverv2.domain.auth.service.ValidateTokenService
import rainbowfriends.daramserverv2.global.security.jwt.service.JwtTokenParserService

@Service
class ValidateTokenServiceImpl(private val jwtTokenParserService: JwtTokenParserService) : ValidateTokenService {
    override fun validateToken(token: String): Boolean {  // 토큰이 유효한지 확인
        return jwtTokenParserService.isTokenValid(token)  // true or false 반환
    }
}