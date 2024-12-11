package rainbowfriends.daramserverv2.domain.checkin.component

import org.springframework.stereotype.Component
import rainbowfriends.daramserverv2.domain.checkin.service.CheckInTransactionService
import rainbowfriends.daramserverv2.global.checkin.component.CheckInDataSync
import rainbowfriends.daramserverv2.global.redis.redisson.util.DistributedLock
import java.time.LocalDate

@Component
class CheckInStatusSwitch(
    private val checkInTransactionService: CheckInTransactionService,
    private val checkInDataSync: CheckInDataSync,
    private val distributedLock: DistributedLock
) {

    fun switchCheckInStatus(studentId: Short?, date: LocalDate): Boolean {
        if (studentId == null) return false
        val lockKey = "check-in:$date"
        return distributedLock.executeWithLock(lockKey, 5L, 10L) {
            handleCheckInStatusSwitch(studentId, date)
        }
    }

    private fun handleCheckInStatusSwitch(studentId: Short, date: LocalDate): Boolean {
        return try {
            val user = checkInTransactionService.getMemberInfo(studentId)
            val checkIn = checkInTransactionService.getCheckInRecord(user, date)
            checkInTransactionService.toggleCheckInStatus(checkIn)
            checkInDataSync.syncCheckInToMongoDB(checkIn)
            true
        } catch (e: Exception) {
            false
        }
    }
}
