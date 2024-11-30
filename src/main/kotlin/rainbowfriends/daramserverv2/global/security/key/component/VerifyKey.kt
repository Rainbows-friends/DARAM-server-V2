package rainbowfriends.daramserverv2.global.security.key.component

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import rainbowfriends.daramserverv2.global.security.key.repository.KeyRepository

@Deprecated(message = "Use JwtTokenParserService instead")
@Component
class VerifyKey(private val keyRepository: KeyRepository) {
    @Transactional
    fun verifyKey(key: String): Boolean {
        return keyRepository.existsByKey(key)
    }
}