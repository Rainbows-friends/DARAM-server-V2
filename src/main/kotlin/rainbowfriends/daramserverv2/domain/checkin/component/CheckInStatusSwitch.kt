package rainbowfriends.daramserverv2.domain.checkin.component

import org.springframework.stereotype.Component
import rainbowfriends.daramserverv2.domain.member.exception.MemberNotFoundException
import rainbowfriends.daramserverv2.global.checkin.component.CheckInDataSync
import rainbowfriends.daramserverv2.global.checkin.repository.CheckInRepository
import rainbowfriends.daramserverv2.global.member.entity.Member
import rainbowfriends.daramserverv2.global.member.repository.MemberRepository
import rainbowfriends.daramserverv2.global.redis.redisson.util.DistributedLock
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
        val grade = studentId.toString()[0].toString().toInt()
        val classNum = studentId.toString()[1].toString().toInt()
        val number = studentId.toString().substring(2).toInt()
        return distributedLock.executeWithLock(lockKey, 5L, 10L) {
            try {
                val user: Member = memberRepository.findByGradeAndClassNumAndNumber(grade, classNum, number)
                    ?: throw MemberNotFoundException("Member not found")
                val checkIn = checkInRepository.findByUserAndCheckinDate(user, date)
                checkIn.checkinStatus = !checkIn.checkinStatus
                checkInRepository.save(checkIn)
                checkInDataSync.syncCheckInToMongoDB(checkIn)
                true
            } catch (e: Exception) {
                false
            }
        }
    }
}