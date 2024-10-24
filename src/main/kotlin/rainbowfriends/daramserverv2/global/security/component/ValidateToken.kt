package rainbowfriends.daramserverv2.global.security.component

import jakarta.transaction.Transactional
import org.springframework.stereotype.Component
import rainbowfriends.daramserverv2.global.security.repository.TokenRepository

@Component
@Transactional
class ValidateToken(private val tokenRepository: TokenRepository) {
    fun validateToken(token: String): Boolean {
        return tokenRepository.existsByToken(token)
    }
}