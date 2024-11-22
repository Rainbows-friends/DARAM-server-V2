package rainbowfriends.daramserverv2.global.redis.redisson.util

import org.redisson.api.RedissonClient
import org.springframework.stereotype.Component
import java.util.concurrent.TimeUnit

@Component
class DistributedLock(
    private val redissonClient: RedissonClient
) {

    fun <T> executeWithLock(lockKey: String, waitTime: Long, leaseTime: Long, action: () -> T): T {
        val lock = redissonClient.getLock(lockKey)
        return if (lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS)) {
            try {
                action()
            } finally {
                lock.unlock()
            }
        } else {
            throw IllegalStateException("Unable to acquire lock on key: $lockKey")
        }
    }
}