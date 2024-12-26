package rainbowfriends.daramserverv2.domain.auth.component

import org.springframework.stereotype.Component
import rainbowfriends.daramserverv2.global.security.key.repository.KeyRepository

@Component
class FindKey(private val keyRepository: KeyRepository) {
    fun findKey(key: String): Boolean {
        return keyRepository.existsByKey(key)  // Key 테이블에소 Key가 존재하는지 확인
    }
}