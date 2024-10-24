package rainbowfriends.daramserverv2.global.security.repository

import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import rainbowfriends.daramserverv2.global.security.entity.Token

@Repository
interface TokenRepository : CrudRepository<Token, String> {
    fun deleteByToken(token: String)
    fun existsByToken(token: String): Boolean
}