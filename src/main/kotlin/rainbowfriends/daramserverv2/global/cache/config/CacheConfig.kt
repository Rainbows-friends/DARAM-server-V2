package rainbowfriends.daramserverv2.global.cache.config

import com.github.benmanes.caffeine.cache.Caffeine.newBuilder
import org.springframework.cache.annotation.EnableCaching
import org.springframework.cache.caffeine.CaffeineCacheManager
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableCaching
class CacheConfig {
    @Bean
    fun cacheManager(): CaffeineCacheManager {
        val cacheManager = CaffeineCacheManager("allMembers", "MemberByStudentId", "MemberByEmail")  // 캐시 매니저 생성(캐시 이름은 allMembers, MemberByStudentId, MemberByEmail)
        cacheManager.setCaffeine(  // Java Caffeine 라이브러리를 사용하여 캐시 설정
            newBuilder()  // 캐시 빌더 생성
                .maximumSize(1000)  // 캐시 최대 크기 설정
                .expireAfterWrite(5, java.util.concurrent.TimeUnit.MINUTES)  // 캐시의 TTL(Time To Live) 설정
        )
        return cacheManager
    }
}