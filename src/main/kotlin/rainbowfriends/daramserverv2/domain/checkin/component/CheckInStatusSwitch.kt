package rainbowfriends.daramserverv2.domain.checkin.component

import org.springframework.stereotype.Component
import rainbowfriends.daramserverv2.domain.checkin.service.CheckInTransaction
import rainbowfriends.daramserverv2.global.redis.redisson.util.DistributedLock
import rainbowfriends.daramserverv2.global.util.ParseStudentId.parseStudentId
import java.time.LocalDate

@Component
class CheckInStatusSwitch(
    private val checkInTransaction: CheckInTransaction,
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
            val parsedStudentId = parseStudentId(studentId.toString())
            val user = checkInTransaction.getMemberDto(parsedStudentId.first, parsedStudentId.second, parsedStudentId.third)
            val checkIn = checkInTransaction.getCheckInRecord(user, date)
            checkInTransaction.checkInDateModify(checkIn)
            checkInTransaction.toggleCheckInStatus(checkIn)
            true
        } catch (_: Exception) {
            false
        }
    }
}
