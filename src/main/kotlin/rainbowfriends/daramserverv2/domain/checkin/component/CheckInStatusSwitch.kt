package rainbowfriends.daramserverv2.domain.checkin.component

import org.springframework.stereotype.Component
import org.springframework.transaction.annotation.Transactional
import rainbowfriends.daramserverv2.domain.member.exception.MemberNotFoundException
import rainbowfriends.daramserverv2.global.checkin.repository.CheckInRepository
import rainbowfriends.daramserverv2.global.checkin.entity.CheckIn
import rainbowfriends.daramserverv2.global.member.entity.Member
import rainbowfriends.daramserverv2.global.member.repository.MemberRepository
import rainbowfriends.daramserverv2.global.redis.redisson.util.DistributedLock
import rainbowfriends.daramserverv2.global.checkin.component.CheckInDataSync
import java.time.LocalDate

@Component
class CheckInStatusSwitch(
    private val checkInRepository: CheckInRepository,
    private val memberRepository: MemberRepository,
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
            val user = getMemberInfo(studentId)
            val checkIn = getCheckInRecord(user, date)
            toggleCheckInStatus(checkIn)
            true
        } catch (e: Exception) {
            false
        }
    }

    @Transactional
    fun getMemberInfo(studentId: Short): Member {
        studentId.toString().let {
            val grade = it[0].toString().toInt()
            val classNum = it[1].toString().toInt()
            val studentNum = it.substring(2).toInt()
            return memberRepository.findByGradeAndClassNumAndNumber(grade, classNum, studentNum)
                ?: throw MemberNotFoundException("Member not found")
        }
    }

    @Transactional
    fun getCheckInRecord(user: Member, date: LocalDate): CheckIn {
        return checkInRepository.findByUserAndCheckinDate(user, date)
            ?: throw IllegalStateException("Check-in record not found")
    }

    @Transactional
    fun toggleCheckInStatus(checkIn: CheckIn) {
        checkIn.checkinStatus = !checkIn.checkinStatus
        checkInRepository.save(checkIn)
        checkInDataSync.syncCheckInToMongoDB(checkIn)
    }
}
