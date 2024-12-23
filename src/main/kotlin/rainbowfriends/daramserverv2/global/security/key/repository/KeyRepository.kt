package rainbowfriends.daramserverv2.global.security.key.repository

import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import rainbowfriends.daramserverv2.global.security.key.entity.Key

@Repository
interface KeyRepository : JpaRepository<Key, String> {
    fun existsByKey(key: String): Boolean
}