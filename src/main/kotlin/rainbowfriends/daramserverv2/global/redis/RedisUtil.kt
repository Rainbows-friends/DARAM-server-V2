package rainbowfriends.daramserverv2.global.redis

import org.springframework.data.redis.core.RedisTemplate
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class RedisUtil(private val redisTemplate: RedisTemplate<String, Any>) {
    fun set(key: String, value: Any, minutes: Int) {  // key, value, minutes를 받아서 Redis에 저장
        redisTemplate.opsForValue().set(key, value, minutes.toLong(), TimeUnit.SECONDS)  // key, value, minutes를 받아서 Redis에 저장, minutes는 초로 변환,TTL(Time To Live) 설정
    }

    fun get(key: String): Any? {  // key를 받아서 Redis에서 조회
        return redisTemplate.opsForValue().get(key)  // key를 받아서 Redis에서 조회
    }

    fun delete(key: String): Boolean {  // key를 받아서 Redis에서 삭제
        return redisTemplate.delete(key)  // key를 받아서 Redis에서 삭제
    }

    fun exists(key: String): Boolean {  // key를 받아서 Redis에 존재하는지 확인
        return redisTemplate.hasKey(key)  // key를 받아서 Redis에 존재하는지 확인
    }
}