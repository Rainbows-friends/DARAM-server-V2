package rainbowfriends.daramserverv2.global.redis

import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.data.redis.connection.RedisConnectionFactory
import org.springframework.data.redis.connection.lettuce.LettuceConnectionFactory
import org.springframework.data.redis.core.RedisTemplate
import org.springframework.data.redis.serializer.GenericJackson2JsonRedisSerializer
import org.springframework.data.redis.serializer.StringRedisSerializer

@Configuration
class RedisConfig(
    @Value("\${spring.data.redis.host}") private val redisHost: String,
    @Value("\${spring.data.redis.port}") private val redisPort: Int
) {

    @Bean
    fun redisConnectionFactory(): RedisConnectionFactory {  // RedisConnectionFactory 빈 등록
        return LettuceConnectionFactory(redisHost, redisPort)
    }

    @Bean
    fun redisTemplate(): RedisTemplate<String, Any> {  // RedisTemplate 빈 등록
        val redisTemplate = RedisTemplate<String, Any>()  // RedisTemplate 객체 생성
        redisTemplate.connectionFactory = redisConnectionFactory()  // RedisConnectionFactory(연결) 설정
        val serializer = GenericJackson2JsonRedisSerializer()  // GenericJackson2JsonRedisSerializer 객체(직렬화) 생성
        redisTemplate.keySerializer = StringRedisSerializer()  // keySerializer(키 직렬화) 설정
        redisTemplate.valueSerializer = serializer  // valueSerializer(값 직렬화) 설정
        redisTemplate.hashKeySerializer = StringRedisSerializer()  // hashKeySerializer(해시키 직렬화) 설정
        redisTemplate.hashValueSerializer = serializer  // hashValueSerializer(해시값 직렬화) 설정
        return redisTemplate
    }
}