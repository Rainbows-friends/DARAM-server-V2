package rainbowfriends.daramserverv2.global.cache.config

import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
@Configuration
@EnableCaching
class CacheConfig {
    @Bean
    fun cacheManager(): CaffeineCacheManager {
        val cacheManager = CaffeineCacheManager("allMembers")
        cacheManager.setCaffeine(
            com.github.benmanes.caffeine.cache.Caffeine.newBuilder()
                .maximumSize(1000)
                .expireAfterWrite(1, java.util.concurrent.TimeUnit.HOURS)
        )
        return cacheManager
    }
}