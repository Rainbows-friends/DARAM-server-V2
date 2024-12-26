package rainbowfriends.daramserverv2.global.redis.redisson.config

import org.redisson.Redisson
import org.redisson.api.RedissonClient
import org.redisson.config.Config
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration


@Configuration
class RedissonConfig {

    @Value("\${spring.data.redis.host}")
    private lateinit var redisHost: String

    @Value("\${spring.data.redis.port}")
    private lateinit var redisPort: String

    @Bean
    fun redissonClient(): RedissonClient {
        val config = Config()
        config.useSingleServer()  // 단일 서버 사용
            .setAddress("redis://$redisHost:$redisPort")  // Redis 주소 설정
        return Redisson.create(config)  // Redisson 클라이언트 생성
    }
}