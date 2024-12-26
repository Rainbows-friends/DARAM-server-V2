package rainbowfriends.daramserverv2.global.redis.redisson.util

import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class DistributedLock(private val redissonClient: RedissonClient) {

    fun <T> executeWithLock(lockKey: String, waitTime: Long, leaseTime: Long, action: () -> T): T {  // Lock을 사용하여 작업 실행
        val lock = redissonClient.getLock(lockKey)  // Lock 객체 생성
        return if (lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS)) {  // Lock을 획득하면
            try {
                action()  // 작업 실행
            } finally {
                lock.unlock()  // Lock 해제
            }
        } else {
            throw IllegalStateException("Unable to acquire lock on key: $lockKey")  // Lock 획득 실패 시 예외 발생
        }
    }
}